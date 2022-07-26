<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn color="black" outlined height="40">Скачать в CSV
          <v-icon color="black" class="ml-2">mdi-tray-arrow-down</v-icon>
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="">
        <v-data-table
          :height="tableHeight"
          :headers="headers"
          fixed-header
          show-expand
          single-expand
          :items="data"
          item-key="partnerId"
          :items-per-page="-1"
          no-data-text="Нет данных"
          hide-default-footer
          id="table"
        >
          <template #item.requisites="{ item }">
            <v-icon @click="editRequisites(item)">mdi-clipboard-outline</v-icon>
          </template>
          <template #expanded-item="{ item }">
            <v-simple-table>
              <template v-slot:default>
                <thead>
                <tr>
                  <th v-for="payment in item.paymentDetails" :key="payment.period" class="text-left">
                    {{ payment.period }}
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td v-for="payment in item.paymentDetails" :key="payment.period">
                    {{ payment.orderCount }} шт./{{ payment.payment }} руб.
                  </td>
                </tr>
                </tbody>
              </template>
            </v-simple-table>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
    <RequisitesEditDialog
      v-if="requisitesEntity"
      v-model="requisitesDialog"
      :entity="requisitesEntity"
      @confirm-edit="submitEditRequisites"
      @cancel-edit="cancelEditRequisites"
    />
    <v-snackbar
      v-model="notification"
      color="black"
      :timeout="3000"
    >
      Данные успешно сохранены!
      <template #action="{ attrs }">
        <v-btn
          color="primary"
          icon
          v-bind="attrs"
          @click="notification = false"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script lang="ts">
import { extend } from 'lodash'
import RequisitesEditDialog from '../../components/dashboard/RequisitesEditDialog.vue'

export default {
  name: 'PaymentsPage',
  components: { RequisitesEditDialog },
  layout: 'dashboard',
  async asyncData({ $axios }) {
    const data = await $axios.$get('/api/payment', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    return { data }
  },
  data() {
    return {
      headers: [
        { text: 'Название партнера', value: 'partnerName', sortable: false, class: 'table__header' },
        { text: 'Логин', value: 'partnerLogin', sortable: false, class: 'table__header' },
        { text: 'URL', value: 'partnerUrl', sortable: false, class: 'table__header' },
        { text: 'Реквизиты', value: 'requisites', sortable: false, align: 'center', class: 'table__header' },
        { text: 'Ставка вознаграждения', value: 'partnerFee', class: 'table__header' },
        { text: '', value: 'data-table-expand', sortable: false, align: 'center', class: 'table__header' }
      ],
      tableHeight: 500,
      requisitesDialog: false,
      requisitesEntity: null,
      requisitesId: null,
      notification: false
    }
  },
  mounted() {
    const table = document.getElementById('table')
    this.tableHeight = this.$vuetify.breakpoint.mobile
      ? window.innerHeight - table.getBoundingClientRect().y - 60
      : window.innerHeight - table.getBoundingClientRect().y - 100
  },
  methods: {
    async editRequisites(entity) {
      this.requisitesEntity = await this.$axios.$get(`/api/admin/users/${entity.partnerLogin}`)
      this.requisitesId = this.data.indexOf(entity)
      this.requisitesDialog = true
    },
    async submitEditRequisites(requisites) {
      await this.$axios.$put('/api/admin/users', extend(this.requisitesEntity, requisites)).then((res) => {
        this.data[this.requisitesId].partnerRequisitesData = res.profile.requisitesData
        this.requisitesEntity = null
        this.requisitesId = null
        this.notification = true
      })
    },
    cancelEditRequisites() {
      this.requisitesEntity = null
      this.requisitesId = null
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

::v-deep .v-data-table > .v-data-table__wrapper tbody tr.v-data-table__expanded__content {
  box-shadow: none;
}
</style>
