<template>
  <v-row justify="center">
    <v-col cols="11" sm="8" lg="4" class="remember-container">
      <h3 class="form__heading">Забыли пароль?</h3>
      <span>Укажите вашу электронную почту и мы отправим ссылку для восстановления пароля</span>
      <v-form ref="form">
        <v-text-field
          v-model="email"
          placeholder="Электронная почта"
          outlined
          :rules="required"
        />
        <v-btn
          color="primary"
          height="70"
          block
          @click="submitRemember"
        >
          Отправить
        </v-btn>
      </v-form>
      <div class="mt-5 text-center">
        <v-btn text to="/dashboard/account/auth">Войти</v-btn>
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'RememberPage',
  layout: 'auth',
  data() {
    return {
      email: '',
      required: [v => !!v || 'Это поле необходимо заполнить']
    }
  },
  methods: {
    submitRemember() {
      if (this.$refs.form.validate()) {
        this.$store.dispatch('user/remember', {
          email: this.email
        })
      }
    }
  }
})
</script>

<style scoped lang="scss">
.remember-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 70px;
  box-shadow: 0 0 30px rgba(154, 164, 255, 0.2);
  border-radius: 16px;

  @media only screen and (max-width: 959px) {
    padding: 40px;
  }

  @media only screen and (max-width: 599px) {
    padding: 20px;
  }
}

.form {
  &__heading {
    font-size: 24px;
    font-weight: 500;
    text-align: center;

    @media only screen and (max-width: 959px) {
      font-size: 18px;
    }

    @media only screen and (max-width: 599px) {
      font-size: 16px;
    }
  }
}
</style>
