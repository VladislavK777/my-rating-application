<template>
  <v-container>
    <v-row>
      <v-col>
        <ButtonBack />
      </v-col>
    </v-row>
    <v-row justify="space-around" align="center" class="form-container">
      <v-col cols="4">
        <FormInfo />
      </v-col>
      <v-col cols="5">
        <FormBlock :loading="loading" :disabled="disabled" @submit="submitForm" />
      </v-col>
    </v-row>
    <v-snackbar
      v-model="paymentNotification.open"
      color="black"
      multi-line
      :timeout="3000"
    >
      {{ paymentNotification.text }}
      <template #action="{ attrs }">
        <v-btn
          color="primary"
          icon
          v-bind="attrs"
          @click="paymentNotification.open = false"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

import ButtonBack from '~/components/base/ButtonBack'
import FormBlock from '~/components/form/FormBlock'
import FormInfo from '~/components/form/FormInfo'

export default {
  name: 'FormPage',
  components: { ButtonBack, FormBlock, FormInfo },
  data() {
    return {
      loading: false,
      disabled: false,
      paymentNotification: {
        open: false,
        text: '',
      },
    }
  },
  created() {
    const sock = new SockJS('http://localhost:8080/websocket/order')
    const stompClient = Stomp.over(sock);
    stompClient.connect({}, function(frame) {
      stompClient.subscribe('/topic/result', function(messageOutput) {
        console.log(JSON.parse(messageOutput.body));
      });
    });
  },
  mounted() {
    if (this.$route.query.orderId && this.$route.query.status && this.$route.query.uid) {
      if (this.$route.query.status === 'successful') {
        this.paymentNotification.text = 'Оплата прошла успешно. Вы можете оставаться на странице, чтобы автоматически перейти на страницу отчета в момент его готовности'
        this.paymentNotification.open = true
        setTimeout(() => {
          this.$axios.$put(`/order/paid/${this.$route.query.orderId}?transactionId=${this.$route.query.uid}`, {}, {
            headers: {
              'X-API-Key': '4d7a8b90-7520-4b19-9c3e-36b8f253225d',
            },
          })
        }, 3000)
      } else {
        this.paymentNotification.text = 'Оплата не была завершена'
        this.paymentNotification.open = true
      }
    }
  },
  methods: {
    async submitForm({ firstName, lastName, passport, email, birthDate }) {
      const orderData = {
        firstName: 'ИВАН',
        lastName: 'ИВАНОВ',
        passportSerial: '1234',
        passportNumber: '123456',
        email: 'mail@mail.asd',
        birthDate: '01.01.1999',
      }
      this.loading = true
      const data = await this.$axios.$post(
        '/order',
        {
          /* orderData: {
            firstName,
            lastName,
            passportSerial: passport.slice(0, 4),
            passportNumber: passport.slice(5, 11),
            email,
            birthDate,
          }, */
          orderData,
        },
        {
          headers: {
            'X-API-Key': '4d7a8b90-7520-4b19-9c3e-36b8f253225d',
          },
        },
      )
      if (data.id) {
        this.submitPayment(data.id, 'mail@mail.asd', 'ИВАН', 'ИВАНОВ')
      } else {
        this.loading = false
      }
    },
    async submitPayment(orderId, email, firstName, lastName) {
      const checkout = {
        test: true,
        transaction_type: 'payment',
        order: {
          amount: 39900,
          currency: 'RUB',
          description: 'Покупка отчета по кредитному рейтингу',
        },
        customer: {
          email,
          first_name: firstName,
          last_name: lastName,
        },
        settings: {
          success_url: `http://localhost:9000/form?orderId=${orderId}`,
          decline_url: `http://localhost:9000/form?orderId=${orderId}`,
          fail_url: `http://localhost:9000/form?orderId=${orderId}`,
          language: 'ru',
        },
      }
      const data = await this.$axios.$post(
        'https://checkout.freepayment.online/ctp/api/checkouts',
        { checkout },
        {
          auth: {
            username: '19010',
            password:
              '5642e1c514e110944ee0840c6e8994951c81396284b3a5d6b8668371f201bc0e',
          },
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
            'X-API-Version': 2,
          },
        },
      )
      if (data.checkout.redirect_url) {
        this.loading = false
        this.disabled = true
        window.location.href = data.checkout.redirect_url
      } else {
        this.loading = false
        this.paymentNotification.text = 'Ошибка платежной системы'
        this.paymentNotification.open = true
      }
    },
  },
}
</script>

<style scoped lang="scss">
.form-container {
  margin-top: 85px !important;
}
</style>
