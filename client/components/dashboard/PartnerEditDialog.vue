<template>
  <v-dialog
    :value="value"
    max-width="1120"
    @click:outside="cancelEdit"
  >
    <v-form ref="form" class="edit-dialog">
      <v-row>
        <v-col><span class="font-weight-medium">Партнер</span></v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="3">
          <v-text-field
            :value="form.profile.partnerName"
            label="Название партнера"
            placeholder="Название партнера"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
            @keypress="isLatin($event)"
            @input="form.profile.partnerName = $event"
          />
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field
            v-model="form.login"
            label="Логин"
            placeholder="Логин"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field
            v-model="form.profile.fee"
            label="Комиссия"
            placeholder="Комиссия"
            outlined
            v-mask="'#########'"
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field
            v-model="form.profile.url"
            label="URL"
            placeholder="URL"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field
            v-model="form.firstName"
            label="Имя"
            placeholder="Имя"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field
            v-model="form.profile.phoneNumber"
            label="Номер телефона"
            placeholder="Номер телефона"
            outlined
            append-icon="mdi-pencil-outline"
            v-mask="'+7 ### ### ## ##'"
            :rules="[required, phone]"
          />
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field
            v-model="form.email"
            label="Электронная почта"
            placeholder="Электронная почта"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required, email]"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col align="end">
          <v-btn color="black" text @click="cancelEdit">Отмена</v-btn>
          <v-btn color="primary" @click="confirmEdit">Сохранить</v-btn>
        </v-col>
      </v-row>
    </v-form>
  </v-dialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'PartnerEditDialog',
  props: {
    value: {
      type: Boolean,
      required: true
    },
    entity: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      form: null,
      required: v => !!v || 'Это поле необходимо заполнить',
      phone: v => v && v.trim().length === 16 || 'Номер телефона указан неверно',
      email: v => /.+@.+\..+/.test(v) || 'Электронная почта указана неверно'
    }
  },
  methods: {
    isLatin(e) {
      const char = String.fromCharCode(e.keyCode)
      if (/^[A-Za-z]+$/.test(char)) return true
      else e.preventDefault()
    },
    cancelEdit() {
      this.$emit('cancel-edit')
      this.$emit('input', false)
    },
    confirmEdit() {
      if (this.$refs.form.validate()) {
        this.$emit('confirm-edit', this.form)
        this.$emit('input', false)
      }
    }
  },
  created() {
    this.form = JSON.parse(JSON.stringify(this.entity))
  }
})
</script>

<style scoped lang="scss">
.edit-dialog {
  padding: 40px;
  background: var(--v-white-base);
  border-radius: 16px;
  box-shadow: 0 0 30px rgba(154, 164, 255, 0.2);
}
</style>
