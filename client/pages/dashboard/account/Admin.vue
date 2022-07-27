<template>
  <v-row justify="center" align="center">
    <v-col cols="11" sm="8" lg="4" class="change-container">
      <h3 class="form__heading">Изменить аккаунт</h3>
      <v-form>
        <div class="form__input">
          <span>Логин</span>
          <v-text-field
            v-model="login"
            type="text"
            placeholder="Логин"
            outlined
          />
        </div>
        <div class="form__input">
          <span>Электронная почта</span>
          <v-text-field
            v-model="email"
            type="text"
            placeholder="Электронная почта"
            outlined
          />
        </div>
        <div class="form__input">
          <span>Пароль</span>
          <v-text-field
            v-model="password"
            :type="showPass ? 'text' : 'password'"
            placeholder="Пароль"
            outlined
            :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPass = !showPass"
          />
        </div>
        <v-btn
          color="primary"
          height="70"
          block
          @click="submitChange"
        >
          Сохранить
        </v-btn>
      </v-form>
      <div class="mt-5 text-center">
        <v-btn text to="/dashboard/partners">На главную</v-btn>
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'AdminChangePage',
  layout: 'change',
  middleware: 'admin',
  data() {
    return {
      login: '',
      email: '',
      password: '',
      showPass: false
    }
  },
  methods: {
    submitChange() {
      this.$store.dispatch('user/adminChange', {
        login: this.login,
        email: this.email,
        password: this.password
      })
    }
  }
})
</script>

<style scoped lang="scss">
.change-container {
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
