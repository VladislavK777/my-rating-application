<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn color="black" outlined height="40">Скачать в CSV
          <v-icon color="black" class="ml-2">mdi-tray-arrow-down</v-icon>
        </v-btn>
        <v-text-field
          v-model="query"
          color="black"
          outlined
          dense
          placeholder="Найти партнера"
          append-icon="mdi-magnify"
          class="ml-10"
          :style="{ width: '290px', display: 'inline-block' }"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="">
        <v-data-table
          :height="tableHeight"
          :headers="headers"
          fixed-header
          :items="data"
          :items-per-page="-1"
          no-data-text="Нет данных"
          :loading="loading"
          loading-text="Поиск..."
          hide-default-footer
          id="table"
        >
          <template #item.edit="{ item }">
            <v-icon @click="editPartner(item)">mdi-pencil-outline</v-icon>
          </template>
          <template #item.requisites="{ item }">
            <v-icon @click="editRequisites(item)">mdi-clipboard-outline</v-icon>
          </template>
          <template #item.createdDate="{ item }">
            {{ new Intl.DateTimeFormat('ru', {
            year: 'numeric',
            month: 'numeric',
            day: 'numeric'
          }).format(new Date(item.createdDate)) }}
          </template>
        </v-data-table>
      </v-col>
    </v-row>
    <PartnerEditDialog
      v-if="editEntity"
      v-model="editDialog"
      :entity="editEntity"
      @confirm-edit="submitEditPartner"
      @cancel-edit="cancelEditPartner"
    />
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
import { extend, debounce } from 'lodash'
import PartnerEditDialog from '../../components/dashboard/PartnerEditDialog.vue'
import RequisitesEditDialog from '../../components/dashboard/RequisitesEditDialog.vue'

export default {
  name: 'PartnersPage',
  components: { RequisitesEditDialog, PartnerEditDialog },
  layout: 'dashboard',
  async asyncData({ $axios }) {
    const data = await $axios.$get('/api/admin/users', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    return { data }
  },
  data() {
    return {
      headers: [
        { text: '', value: 'edit', sortable: false, align: 'center', class: 'table__header' },
        { text: 'Реквизиты', value: 'requisites', sortable: false, align: 'center', class: 'table__header' },
        { text: 'Название партнера', value: 'profile.partnerName', sortable: false, class: 'table__header' },
        { text: 'Логин', value: 'login', sortable: false, class: 'table__header' },
        { text: 'URL', value: 'profile.url', sortable: false, class: 'table__header' },
        { text: 'Дата регистрации', value: 'createdDate', class: 'table__header' },
        { text: 'Имя', value: 'firstName', sortable: false, class: 'table__header' },
        { text: 'Телефон', value: 'profile.phoneNumber', sortable: false, class: 'table__header' },
        { text: 'Email', value: 'email', sortable: false, class: 'table__header' },
        { text: 'Реферальная ссылка', value: 'profile.referenceLink', sortable: false, class: 'table__header' },
        { text: 'Ставка вознаграждения', value: 'profile.fee', class: 'table__header' }
      ],
      tableHeight: 500,
      query: '',
      loading: false,
      editDialog: false,
      editEntity: null,
      editId: null,
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
    search: debounce(async function(query) {
      this.loading = true
      this.data = await this.$axios.$get(`/api/admin/users?partnerName.contains=${query}`)
      this.loading = false
    }, 300),
    editPartner(partner) {
      this.editEntity = partner
      this.editId = this.data.indexOf(partner)
      this.editDialog = true
    },
    async submitEditPartner(partner) {
      await this.$axios.$put('/api/admin/users', extend(this.editEntity, partner)).then((res) => {
        this.data[this.editId] = res
        this.editEntity = null
        this.editId = null
        this.notification = true
      })
    },
    cancelEditPartner() {
      this.editEntity = null
      this.editId = null
    },
    editRequisites(requisites) {
      this.requisitesEntity = requisites
      this.requisitesId = this.data.indexOf(requisites)
      this.requisitesDialog = true
    },
    async submitEditRequisites(requisites) {
      await this.$axios.$put('/api/admin/users', extend(this.requisitesEntity, requisites)).then((res) => {
        this.data[this.requisitesId] = res
        this.requisitesEntity = null
        this.requisitesId = null
        this.notification = true
      })
    },
    cancelEditRequisites() {
      this.requisitesEntity = null
      this.requisitesId = null
    },
    compareDates(a, b) {
      const dateA = '25.07.2022'.split('.')
      const dateB = '24.07.2022'.split('.')
      return new Date(dateA[1] - 1, dateA[0], dateA[2]) > new Date(dateB[1] - 1, dateB[0], dateB[2])
    }
  },
  watch: {
    query(value) {
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
