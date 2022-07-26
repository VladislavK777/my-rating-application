<template>
  <v-row justify="center" align="center">
    <v-col cols="11" sm="8" lg="4" class="reset-container">
      <h3 class="form__heading">Восстановление пароля</h3>
      <v-form>
        <div class="form__input">
          <span>Укажите новый пароль</span>
          <v-text-field
            v-model="password"
            :type="showPass ? 'text' : 'password'"
            placeholder="Пароль"
            outlined
            :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
            hint="Минимум 8 символов, латиница, цифры и специальные символы"
            :rules="[...required, passwordRule]"
            @click:append="showPass = !showPass"
          />
        </div>
        <div class="form__input">
          <span>Повторите пароль</span>
          <v-text-field
            v-model="passwordRepeat"
            :type="showRepeat ? 'text' : 'password'"
            placeholder="Пароль"
            outlined
            :append-icon="showRepeat ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[...required, ...equalPass]"
            @click:append="showRepeat = !showRepeat"
          />
        </div>
        <v-btn
          color="primary"
          height="70"
          block
          @click="submitReset"
        >
          Сохранить
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
  name: 'ResetPage',
  layout: 'auth',
  middleware: [
    function({ route, redirect }) {
      if (!route.query.key) {
        redirect('/dashboard/account/remember');
      }
    },
  ],
  data() {
    return {
      password: '',
      showPass: false,
      passwordRepeat: '',
      showRepeat: false,
      required: [v => !!v || 'Это поле необходимо заполнить'],
      passwordRule: v => /^[A-Za-z\d@$!%*#?&]{8,}$/.test(v) || 'Пароль не соответствует требованиям',
      equalPass: [v => v === this.password || 'Пароли не совпадают']
    }
  },
  methods: {
    submitReset() {
      if (this.$refs.form.validate()) {
        this.$store.dispatch('user/reset', {
          key: this.$route.query.key,
          newPassword: this.password,
          repeatNewPassword: this.passwordRepeat
        })
      }
    }
  }
})
</script>

<style scoped lang="scss">
.reset-container {
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
  &-container {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }

  &__input {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }

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
