<template>
  <div class="info-container">
    <div>
      <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг" class="info__logo">
      <p class="info__text">Вы будете перенаправлены на страницу оплаты на сайт платёжной системы FREEPAYMENT.

        Сразу после оплаты на вашу электронную почту будет отправлена ссылка на отчёт.</p>
    </div>
    <div class="info-container--sub">
      <span class="info__heading font-weight-medium">Надежно защищенные способы оплаты</span>
      <img src="~/assets/payment_icons.png" height="36" width="150" alt="Способы оплаты" />
      <p class="info__text--secondary">
        Оплата принимается через платёжную систему FREEPAYMENT (соответствует требованиям, учреждённым международными
        платёжными системами Visa, Mastercard, American Express, JCB и Discover, а также стандарту безопасности PCI
        DSS).
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FormInfo',
}
</script>

<style scoped lang="scss">
.info {
  &-container {
    display: flex;
    flex-direction: column;
    gap: 100px;

    &--sub {
      display: flex;
      flex-direction: column;
      gap: 20px;
    }
  }

  &__logo {
    margin-bottom: 20px;
  }

  &__text {
    white-space: pre-line;
    margin: 0;

    &--secondary {
      margin: 0;
      opacity: 0.4;
    }
  }
}
</style>
