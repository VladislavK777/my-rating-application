<template>
  <v-container fluid>
    <v-row justify-md="space-around" align-content="end" class="footer">
      <v-col md="4" class="footer-container"
             :class="{ 'footer-container--mobile': isMobile, 'footer-container--left': !isMobile  }">
        <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг" class="footer__logo">
        <span>Наши реквизиты</span>
        <span>Мой рейтинг © 2011–2022</span>
      </v-col>
      <v-col md="4" class="footer-container"
             :class="{ 'footer-container--mobile': isMobile, 'footer-container--center': !isMobile  }">
        <span>Политика конфиденциальности</span>
        <span>Способ оплаты и условия возврата</span>
      </v-col>
      <v-col md="4" class="footer-container"
             :class="{ 'footer-container--mobile': isMobile, 'footer-container--right': !isMobile  }">
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
  computed: {
    isMobile() {
      return this.$vuetify.breakpoint.mobile
    },
  },
}
</script>

<style scoped lang="scss">
.footer-container {
  display: flex;
  flex-direction: column;
  flex-grow: 0;
  justify-content: flex-end;
  gap: 10px;

  &--mobile {
    padding: 10px 24px;
    flex-grow: 1;
  }

  &--left {
    text-align: left;
  }

  &--center {
    text-align: center;
  }

  &--right {
    text-align: right;
  }
}

.footer {
  padding: 70px 60px;

  &__logo {
    align-self: start;
    margin-bottom: 15px;
  }
}
</style>
