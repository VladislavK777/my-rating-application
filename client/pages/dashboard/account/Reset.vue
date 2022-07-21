<template>
  <v-row justify="center" align="center">
    <v-col cols="12" md="5" class="reset-container">
      <h3 class="form__heading">Восстановление пароля</h3>
      <v-form>
        <div class="form__input">
          <span>Укажите новый пароль</span>
          <v-text-field
            v-model="password"
            type="password"
            placeholder="Пароль"
            outlined
            :rules="required"
          />
        </div>
        <div class="form__input">
          <span>Повторите пароль</span>
          <v-text-field
            v-model="passwordRepeat"
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
          @click="submitReset"
        >
          Сохранить
        </v-btn>
      </v-form>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'ResetPage',
  layout: 'auth',
  data() {
    return {
      password: '',
      passwordRepeat: '',
      required: [v => !!v || 'Это поле необходимо заполнить']
    }
  },
  methods: {
    submitReset() {
      if (this.$refs.form.validate()) {
        this.$store.dispatch('user/reset', {
          key: 'key',
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
      font-size: 20px;
    }

    @media only screen and (max-width: 599px) {
      font-size: 18px;
    }
  }
}
</style>
