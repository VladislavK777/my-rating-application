<template>
  <div class="payment-container">
    <img src="~/assets/logo.svg" height="29" alt="Мой рейтинг" class="logo">
    <div class="payment__text-box">
      <span class="payment__text">Ошибка обработки платежа.</span>
      <span class="payment__text">Для формирования отчета необходимо заново заполнить форму и совершить оплату.</span>
      <span class="payment__text">Вы будете перенаправлены на страницу формы.</span>
      <v-btn color="primary" height="73" width="260" to="/">Перейти</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FailurePage',
  mounted() {
    setTimeout(() => {
      this.$router.push('/')
    }, 6000)
  }
}
</script>

<style scoped lang="scss">
.payment {
  &-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 60px;
  }

  &__text {
    display: block;
    width: 60%;
    font-weight: 500;
    font-size: 24px;
    text-align: center;

    &-box {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  }
}
</style>
