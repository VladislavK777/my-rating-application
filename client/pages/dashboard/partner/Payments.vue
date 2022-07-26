<template>
  <div :class="{ 'px-2': $vuetify.breakpoint.mobile }">
    <v-row>
      <v-col cols="12">
        <span class="font-weight-medium mr-5">Выбрать год</span>
        <v-select
          v-model="year"
          color="black"
          outlined
          label="Год"
          dense
          hide-details
          :items="[2022]"
          :style="{ width: '290px', display: 'inline-block' }"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" lg="8">
        <v-data-table
          :headers="headers"
          fixed-header
          :items="data"
          :items-per-page="-1"
          no-data-text="Нет данных"
          hide-default-footer
          :loading="loading"
          loading-text="Поиск..."
          id="table"
        >
          <template #item.payment="{ item }">
            {{ item.payment }} руб.
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
export default {
  name: 'PartnerPaymentsPage',
  layout: 'partner',
  async asyncData({ $axios }) {
    const data = await $axios.$get(`/api/payment/partner?year=${new Date().getFullYear()}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    return { data }
  },
  data() {
    return {
      headers: [
        { text: 'Месяц', value: 'monthName', sortable: false, class: 'table__header' },
        { text: 'Сумма', value: 'payment', sortable: false, class: 'table__header' },
        { text: 'Количество оплаченных отчетов', value: 'orderCount', sortable: false, class: 'table__header' }
      ],
      year: new Date().getFullYear(),
      loading: false
    }
  },
  methods: {
    async search(year) {
      this.loading = true
      this.data = await this.$axios.$get(`/api/payment/partner?year=${year}`)
      this.loading = false
    }
  },
  watch: {
    year(value) {
      this.search(value)
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
