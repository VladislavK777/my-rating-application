<template>
  <v-form ref="form">
    <v-row>
      <v-col><span class="font-weight-medium">Общие данные</span></v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          :value="form.profile.partnerName"
          label="Название партнера"
          placeholder="Название партнера"
          outlined
          :rules="[required]"
          @keypress="isLatin($event)"
          @input="form.profile.partnerName = $event"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.login"
          label="Логин"
          placeholder="Логин"
          outlined
          :rules="[required]"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          :value="form.password"
          label="Пароль"
          placeholder="Пароль"
          outlined
          :rules="[required, min4]"
          @keypress="isPassword($event)"
          @input="form.password = $event"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.profile.fee"
          label="Комиссия"
          placeholder="Комиссия"
          outlined
          v-mask="'#########'"
          :rules="[required]"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.profile.url"
          label="URL"
          placeholder="URL"
          outlined
          :rules="[required]"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.firstName"
          label="Имя"
          placeholder="Имя"
          outlined
          :rules="[required]"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.profile.phoneNumber"
          label="Номер телефона"
          placeholder="Номер телефона"
          outlined
          v-mask="'+7 ### ### ## ##'"
          :rules="[required, phone]"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          :value="date"
          label="Дата регистрации"
          placeholder="Дата регистрации"
          outlined
          disabled
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.email"
          label="Электронная почта"
          placeholder="Электронная почта"
          outlined
          :rules="[required, email]"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col><span class="font-weight-medium">Реквизиты</span></v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.profile.requisitesData.cardNumber"
          label="Номер банковской карты"
          placeholder="Номер банковской карты"
          outlined
          v-mask="'#### #### #### ####'"
          :rules="[required]"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.profile.requisitesData.wallet"
          label="Номер электронного кошелька"
          placeholder="Номер электронного кошелька"
          outlined
          :rules="[required]"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.profile.requisitesData.bankName"
          label="Название банка"
          placeholder="Название банка"
          outlined
          :rules="[required]"
        />
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-text-field
          v-model="form.profile.requisitesData.walletName"
          label="Название кошелька"
          placeholder="Название кошелька"
          outlined
          :rules="[required]"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-btn color="primary" height="70" block @click="submitRegistration">Сохранить</v-btn>
      </v-col>
    </v-row>
    <v-snackbar
      v-model="notification"
      color="black"
      :timeout="3000"
    >
      Данные успешно сохранены!
      <template #action="{ attrs }">
        <v-btn
          color="primary"
          icon
          v-bind="attrs"
          @click="notification = false"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </v-form>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'RegistrationIndividualPage',
  layout: 'dashboard',
  data() {
    return {
      form: {
        login: '',
        firstName: '',
        email: '',
        password: '',
        profile: {
          profileType: 'INDIVIDUAL_ENTITY',
          partnerName: '',
          phoneNumber: '',
          fee: '',
          url: '',
          requisitesData: {
            wallet: '',
            walletName: '',
            bankName: '',
            cardNumber: ''
          }
        }
      },
      required: v => !!v || 'Это поле необходимо заполнить',
      phone: v => v && v.trim().length === 16 || 'Номер телефона указан неверно',
      email: v => /.+@.+\..+/.test(v) || 'Электронная почта указана неверно',
      min4: v => v && v.length > 3 || 'Минимум 4 символа',
      notification: false
    }
  },
  computed: {
    date() {
      return new Intl.DateTimeFormat('ru', {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric'
      }).format(new Date())
    }
  },
  methods: {
    isLatin(e) {
      const char = String.fromCharCode(e.keyCode)
      if (/^[A-Za-z]+$/.test(char)) return true
      else e.preventDefault()
    },
    isPassword(e) {
      const char = String.fromCharCode(e.keyCode)
      if (/^[A-Za-z\d#?!@$%^&*-]+$/.test(char)) return true
      else e.preventDefault()
    },
    async submitRegistration() {
      if (this.$refs.form.validate()) {
        await this.$axios.$post('/api/admin/users', this.form).then(() => {
          this.notification = true
          this.$refs.form.reset()
        })
      }
    }
  }
})
</script>

<style scoped lang="scss">

</style>
