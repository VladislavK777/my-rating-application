<template>
  <div>
    <v-fade-transition leave-absolute>
      <div v-if="!success" class="payment-container">
        <img src="~/assets/logo.svg" height="29" alt="Мой рейтинг">
        <div class="payment__text-box">
          <span class="payment__text" :class="{ 'payment__text--active': active === 1 }">Собираем информацию о ваших кредитах</span>
          <span class="payment__text" :class="{ 'payment__text--active': active === 2 }">Расчитываем ваш кредитный рейтинг</span>
          <span class="payment__text" :class="{ 'payment__text--active': active === 3 }">Формируем отчёт</span>
        </div>
        <v-progress-circular size="160" width="20" :value="value" color="primary">
          {{ value }}%
        </v-progress-circular>
      </div>
    </v-fade-transition>
    <v-fade-transition>
      <div v-if="success" class="payment-container">
        <img src="~/assets/logo.svg" height="29" alt="Мой рейтинг" class="logo">
        <div class="payment__text-box">
          <span class="payment__text">Отчёт по вашему кредитному рейтингу готов</span>
          <span class="payment__text">Сейчас вы будете перенаправлены</span>
        </div>
        <img src="~/assets/report.svg" height="200" alt="Отчет">
      </div>
    </v-fade-transition>
    <v-fade-transition>
      <div v-if="success === 'fail'" class="payment-container">
        <img src="~/assets/logo.svg" height="29" alt="Мой рейтинг" class="logo">
        <div class="payment__text-box">
          <span class="payment__text">К сожалению, что-то пошло не так</span>
          <span class="payment__text">Обратитесь в службу поддержки</span>
        </div>
        <v-btn color="primary" height="73" width="260" to="/">Главная</v-btn>
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
      pending: false,
      interval: {},
      value: 0,
      active: 1,
      success: false,
      reportLink: null,
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
          this.reportLink = data.reportLink
        }
      })
    })
    this.interval = setInterval(() => {
      if (this.value === 100) {
        clearInterval(this.interval)
        if (!this.pending && this.reportLink) {
          this.success = true
          setTimeout(() => {
            this.$router.push({ path: `/report/${this.reportLink}` })
          }, 3000)
        } else if (!this.pending && !this.reportLink) {
          this.success = 'fail'
        }
        return
      } else if (this.value > 66) {
        this.active = 3
      } else if (this.value > 33) {
        this.active = 2
      }
      this.value += 2
    }, 200)
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
    clearInterval(this.interval)
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
    opacity: 0.3;
    transition: all .2s;

    &--active {
      opacity: 1;
    }

    &-box {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  }
}
</style>
