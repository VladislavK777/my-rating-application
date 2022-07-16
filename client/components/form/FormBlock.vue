<template>
  <div class="form-block-container">
    <h3 class="form__heading">Узнать свой кредитный рейтинг</h3>
    <v-form
      ref="form"
      v-model="valid"
      :disabled="disabled"
    >
      <v-text-field
        :value="form.lastName.toUpperCase()"
        placeholder="ФАМИЛИЯ"
        outlined
        :rules="required"
        @keypress="isCyrillic($event)"
        @input="form.lastName = $event.toUpperCase()"
      />
      <v-text-field
        :value="form.firstName.toUpperCase()"
        placeholder="ИМЯ"
        outlined
        :rules="required"
        @keypress="isCyrillic($event)"
        @input="form.firstName = $event.toUpperCase()"
      />
      <v-text-field
        v-model="form.passport"
        v-mask="'#### ######'"
        placeholder="СЕРИЯ И НОМЕР ПАСПОРТА"
        outlined
        :rules="required"
      />
      <v-text-field
        v-model="form.birthDate"
        v-mask="'##.##.####'"
        placeholder="ДАТА РОЖДЕНИЯ"
        outlined
        :rules="required"
      />
      <v-text-field
        v-model="form.email"
        placeholder="ЭЛЕКТРОННАЯ ПОЧТА"
        outlined
        :rules="email"
      />
      <v-btn
        color="primary"
        height="75"
        block
        :loading="loading"
        :disabled="disabled"
        @click="validateForm"
      >
        Получить отчёт | 399 ₽
      </v-btn>
      <div class="form__checkbox">
        <v-checkbox v-model="isAgreed" :rules="agreement">
          <template #label>
          <span>
            Я согласен (-на) на обработку
            <NuxtLink to="/">персональных данных</NuxtLink> и с
            <NuxtLink to="/">договором-офертой</NuxtLink>
          </span>
          </template>
        </v-checkbox>
      </div>
    </v-form>
  </div>
</template>

<script>
export default {
  name: 'FormBlock',
  props: {
    loading: {
      type: Boolean,
      required: true,
    },
    disabled: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      form: {
        lastName: '',
        firstName: '',
        passport: '',
        birthDate: '',
        email: '',
      },
      isAgreed: true,
      required: [v => !!v || 'Это поле необходимо заполнить'],
      email: [
        v => !!v || 'Это поле необходимо заполнить',
        v => /.+@.+\..+/.test(v) || 'Электронная почта указана неверно',
      ],
      agreement: [v => !!v || 'Необходимо согласие на обработку персональных данных и с договором-офертой'],
      valid: false,
    }
  },
  methods: {
    validateForm() {
      if (this.$refs.form.validate()) {
        this.$emit('submit', this.form)
      }
    },
    isCyrillic(e) {
      const char = String.fromCharCode(e.keyCode)
      if (/^[А-Яа-я]+$/.test(char)) return true
      else e.preventDefault()
    },
  },
}
</script>

<style scoped lang="scss">
.form-block-container {
  display: flex;
  flex-direction: column;
  padding: 55px 45px;
  background: var(--v-white-base);
  box-shadow: 0 0 30px rgba(154, 164, 255, 0.2);
  border-radius: 16px;
}

.form {
  &__heading {
    font-size: 24px;
    font-weight: 500;
    margin-bottom: 30px;
    text-align: center;

    @media only screen and (max-width: 959px) {
      font-size: 20px;
    }

    @media only screen and (max-width: 599px) {
      font-size: 18px;
    }
  }

  &__checkbox {
    & ::v-deep .v-messages {
      min-height: 30px;
    }
  }
}
</style>
