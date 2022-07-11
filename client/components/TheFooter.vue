<template>
  <v-container class="footer">
    <v-row justify="space-around" align-content="end">
      <v-col cols="4" class="footer-container footer-container--left">
        <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг" class="footer__logo">
        <span>Наши реквизиты</span>
        <span>Мой рейтинг © 2011–2022</span>
      </v-col>
      <v-col cols="4" class="footer-container footer-container--center">
        <span>Политика конфиденциальности</span>
        <span>Способ оплаты и условия возврата</span>
      </v-col>
      <v-col cols="4" class="footer-container footer-container--right">
        <span class="font-weight-medium">Поддержка</span>
        <span>WhatsApp</span>
        <span>support@moyreyting.ru</span>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'TheFooter',
}
</script>

<style scoped lang="scss">
.footer-container {
  display: flex;
  flex-direction: column;
  flex-grow: 0;
  justify-content: flex-end;
  gap: 10px;

  &--left {
    text-align: left;
  }

  &--center {
    text-align: left;
  }

  &--right {
    text-align: right;
  }
}

.footer {
  margin-top: 100px;
  margin-bottom: 100px;
  padding: 0;

  &__logo {
    align-self: start;
    margin-bottom: 15px;
  }
}
</style>
