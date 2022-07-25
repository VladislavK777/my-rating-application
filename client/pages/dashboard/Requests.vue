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
          :items="data"
          :items-per-page="-1"
          no-data-text="Нет данных"
          hide-default-footer
          id="table"
        >
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
  </div>
</template>

<script lang="ts">

export default {
  name: 'RequestsPage',
  layout: 'dashboard',
  async asyncData({ $axios }) {
    const data = (await $axios.$get('/api/order', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })).content
    return { data }
  },
  data() {
    return {
      headers: [
        { text: 'Номер запроса', value: 'id', sortable: false, class: 'table__header' },
        { text: 'Дата запроса', value: 'createdDate', sortable: false, class: 'table__header' },
        { text: 'Фамилия', value: 'lastName', sortable: false, class: 'table__header' },
        { text: 'Ссылка на отчёт', value: 'urlReport', sortable: false, class: 'table__header' },
        { text: 'Статус', value: 'status', sortable: false, class: 'table__header' },
        { text: 'Email', value: 'email', sortable: false, class: 'table__header',  },
        { text: 'Название партнёра', value: 'partnerName', sortable: false, class: 'table__header' },
        { text: 'Логин', value: 'login', sortable: false, class: 'table__header' },
      ],
      tableHeight: 500
    }
  },
  mounted() {
    const table = document.getElementById('table')
    this.tableHeight = this.$vuetify.breakpoint.mobile
      ? window.innerHeight - table.getBoundingClientRect().y - 60
      : window.innerHeight - table.getBoundingClientRect().y - 100
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
