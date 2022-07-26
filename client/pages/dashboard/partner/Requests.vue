<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn v-if="!$vuetify.breakpoint.xs" color="black" outlined height="40">Скачать в CSV
          <v-icon color="black" class="ml-2">mdi-tray-arrow-down</v-icon>
        </v-btn>
      </v-col>
    </v-row>
    <div class="mt-5">
      <span>Количество запросов всего/оплаченные:</span>
      <span class="ml-6 font-weight-medium">{{ data.all }}/{{ data.allPaid }}</span>
    </div>
    <div class="mt-5 mb-5">
      <span>Количество запросов за период всего/оплаченные:</span>
      <span class="ml-6 font-weight-medium">{{ data.allPeriod }}/{{ data.allPeriodPaid }}</span>
    </div>
    <v-row>
      <v-col cols="12" sm="6" md="4" lg="2">
        <v-select
          v-model="status"
          color="black"
          outlined
          label="Статус"
          dense
          :items="statuses"
          item-text="name"
          item-value="alias"
          clearable
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="2">
        <DatePicker v-model="period" @clear="search" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" lg="6">
        <v-data-table
          :height="tableHeight"
          :headers="headers"
          fixed-header
          :items="data.orders"
          :items-per-page="-1"
          no-data-text="Нет данных"
          hide-default-footer
          :loading="loading"
          loading-text="Поиск..."
          id="table"
        >
          <template #item.createdDate="{ item }">
            {{ new Intl.DateTimeFormat('ru', {
            year: 'numeric',
            month: 'numeric',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
          }).format(new Date(item.createdDate)) }}
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import { debounce } from 'lodash'
import DatePicker from '../../../components/base/DatePicker.vue'

export default {
  name: 'PartnerRequestsPage',
  components: { DatePicker },
  layout: 'partner',
  async asyncData({ $axios }) {
    const data = await $axios.$get('/api/order/partner', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    const statuses = await $axios.$get('/api/catalog/order-status', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    return { data, statuses }
  },
  data() {
    return {
      headers: [
        { text: 'Номер запроса', value: 'id', sortable: false, class: 'table__header' },
        { text: 'Дата и время', value: 'createdDate', sortable: false, class: 'table__header' },
        { text: 'Статус', value: 'status', sortable: false, class: 'table__header' }
      ],
      tableHeight: 500,
      status: '',
      period: ['', ''],
      loading: false
    }
  },
  mounted() {
    const table = document.getElementById('table')
    const footer = document.getElementById('footer')
    this.tableHeight = this.$vuetify.breakpoint.mobile
      ? 'undefined'
      : footer.getBoundingClientRect().y - table.getBoundingClientRect().y + 100
  },
  methods: {
    search: debounce(async function() {
      this.loading = true
      this.data = await this.$axios.$get(`/api/order/partner?dateFrom=${this.period[0]}&dateTo=${this.period[1]}&status=${this.status || ''}`)
      this.loading = false
    }, 300)
  },
  watch: {
    status() {
      this.search()
    },
    period(value) {
      if (value[0] && value[1]) {
        this.search()
      }
    }
  }
}
</script>

<style scoped lang="scss">


::v-deep .table {
  &__header {
    color: var(--v-black-base) !important;
    font-weight: 600;
    line-height: 20px;
    background: var(--v-light-grey-base) !important;
    box-shadow: inset 0px -1px 0px #E4E4EF !important;
  }
}
</style>
