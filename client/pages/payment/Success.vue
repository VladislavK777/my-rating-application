<template>
  <div>
    <v-fade-transition leave-absolute>
      <div v-if="pending" class="payment-container">
        <img src="~/assets/logo.svg" height="29" alt="Мой рейтинг">
        <div class="payment__text-box">
          <span class="payment__text">Расчитываем ваш кредитный рейтинг</span>
          <span class="payment__text">Вы можете остаться на этой странице, чтобы автоматически перейти на страницу отчета, когда он будет готов</span>
        </div>
        <v-progress-circular size="160" width="20" indeterminate color="primary"></v-progress-circular>
      </div>
    </v-fade-transition>
    <v-fade-transition>
      <div v-if="!pending" class="payment-container">
        <img src="~/assets/logo.svg" height="29" alt="Мой рейтинг" class="logo">
        <div class="payment__text-box">
          <span class="payment__text">Отчёт по вашему кредитному рейтингу готов</span>
          <span class="payment__text">Сейчас вы будете перенаправлены</span>
        </div>
        <img src="~/assets/report.svg" height="200" alt="Отчет">
      </div>
    </v-fade-transition>
  </div>
</template>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

export default {
  name: 'SuccessPage',
  data() {
    return {
      pending: true,
    }
  },
  mounted() {
    this.sock = new SockJS('http://localhost:8080/websocket/order')
    const stompClient = Stomp.over(this.sock)
    stompClient.connect({}, () => {
      stompClient.subscribe('/topic/result', messageOutput => {
        const data = JSON.parse(messageOutput.body)
        if (data.reportLink) {
          this.pending = false
          this.$router.push({ path: `/report/${data.reportLink}` })
        }
      })
    })
    if (this.$route.query.orderId && this.$route.query.status === 'successful' && this.$route.query.uid) {
      setTimeout(() => {
        this.$axios.$put(`/order/paid/${this.$route.query.orderId}?transactionId=${this.$route.query.uid}`, {}, {
          headers: {
            'X-API-Key': '4d7a8b90-7520-4b19-9c3e-36b8f253225d',
          },
        })
      }, 3000)
    }
  },
  beforeDestroy() {
    this.sock.close()
  },
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
