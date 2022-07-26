<template>
  <v-row justify="center">
    <v-col cols="11" sm="8" lg="4" class="auth-container">
      <h3 class="form__heading">Вход в личный кабинет</h3>
      <v-form ref="form">
        <div class="form__input">
          <span>Укажите логин</span>
          <v-text-field
            v-model="login"
            placeholder="Логин"
            outlined
            :rules="required"
          />
        </div>
        <div class="form__input">
          <span>Укажите пароль</span>
          <v-text-field
            v-model="password"
            type="password"
            placeholder="Пароль"
            outlined
            :rules="required"
          />
        </div>
        <v-btn
          color="primary"
          height="70"
          block
          @click="submitAuth"
        >
          Войти
        </v-btn>
        <div class="mt-5 text-center">
          <span class="mr-3">Забыли пароль?</span>
          <v-btn text to="/dashboard/account/remember">Восстановить</v-btn>
        </div>
      </v-form>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'AuthPage',
  layout: 'auth',
  data() {
    return {
      login: '',
      password: '',
      required: [v => !!v || 'Это поле необходимо заполнить']
    }
  },
  methods: {
    submitAuth() {
      if (this.$refs.form.validate()) {
        this.$store.dispatch('user/login', {
          username: this.login,
          password: this.password,
          rememberMe: true
        }).then(() => {
          this.$router.push('/dashboard')
        })
      }
    }
  }
})
</script>

<style scoped lang="scss">
.auth-container {
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
    margin-bottom: 30px;
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
