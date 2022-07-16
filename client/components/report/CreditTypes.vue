<template>
  <div class="container">
    <h2 class="heading">Типы кредитов</h2>
    <v-simple-table class="table">
      <template v-slot:default>
        <thead>
        <tr>
          <th class="table__header">
            Продукт
          </th>
          <th class="table__header">
            Открытые
          </th>
          <th class="table__header">
            Закрытые
          </th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td class="table__cell">Ипотека</td>
          <td class="table__cell" :class="{ 'table__bold': typesData.ipotecaCredit.open }">
            {{ typesData.ipotecaCredit.open ? 'Да' : 'Нет' }}
          </td>
          <td class="table__cell" :class="{ 'table__bold': typesData.ipotecaCredit.close }">
            {{ typesData.ipotecaCredit.close ? 'Да' : 'Нет' }}
          </td>
        </tr>
        <tr>
          <td class="table__cell">Потребительский кредит</td>
          <td class="table__cell" :class="{ 'table__bold': typesData.potrebCredit.open }">
            {{ typesData.potrebCredit.open ? 'Да' : 'Нет' }}
          </td>
          <td class="table__cell" :class="{ 'table__bold': typesData.potrebCredit.close }">
            {{ typesData.potrebCredit.close ? 'Да' : 'Нет' }}
          </td>
        </tr>
        <tr>
          <td class="table__cell">МФО</td>
          <td class="table__cell" :class="{ 'table__bold': typesData.mfoCredit.open }">
            {{ typesData.mfoCredit.open ? 'Да' : 'Нет' }}
          </td>
          <td class="table__cell" :class="{ 'table__bold': typesData.mfoCredit.close }">
            {{ typesData.mfoCredit.close ? 'Да' : 'Нет' }}
          </td>
        </tr>
        <tr>
          <td class="table__cell">Автокредит</td>
          <td class="table__cell" :class="{ 'table__bold': typesData.autoCredit.open }">
            {{ typesData.autoCredit.open ? 'Да' : 'Нет' }}
          </td>
          <td class="table__cell" :class="{ 'table__bold': typesData.autoCredit.close }">
            {{ typesData.autoCredit.close ? 'Да' : 'Нет' }}
          </td>
        </tr>
        <tr>
          <td class="table__cell">Кредитная карта</td>
          <td class="table__cell" :class="{ 'table__bold': typesData.creditCard.open }">
            {{ typesData.creditCard.open ? 'Да' : 'Нет' }}
          </td>
          <td class="table__cell" :class="{ 'table__bold': typesData.creditCard.close }">
            {{ typesData.creditCard.close ? 'Да' : 'Нет' }}
          </td>
        </tr>
        <tr>
          <td class="table__cell">Кредит под залог</td>
          <td class="table__cell" :class="{ 'table__bold': typesData.otherCredit.open }">
            {{ typesData.otherCredit.open ? 'Да' : 'Нет' }}
          </td>
          <td class="table__cell" :class="{ 'table__bold': typesData.otherCredit.close }">
            {{ typesData.otherCredit.close ? 'Да' : 'Нет' }}
          </td>
        </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
export default {
  name: 'CreditTypes',
  props: {
    typesData: {
      type: Object,
      required: true,
    },
  },
}
</script>

<style scoped lang="scss">
.table {
  border: 1px solid rgba(39, 39, 39, 0.15);
  border-radius: 8px;

  &__header {
    color: var(--v-black-base) !important;
    font-weight: 500 !important;
    font-size: 16px !important;

    @media only screen and (max-width: 599px) {
      font-size: 14px !important;
    }
  }

  &__cell {
    color: var(--v-black-base) !important;
    font-size: 16px !important;

    @media only screen and (max-width: 599px) {
      font-size: 14px !important;
    }
  }

  &__bold {
    font-weight: 600;
  }
}
</style>
