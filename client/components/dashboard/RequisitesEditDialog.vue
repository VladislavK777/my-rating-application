<template>
  <v-dialog
    :value="value"
    max-width="1120"
    @click:outside="cancelEdit"
  >
    <v-form ref="form" class="edit-dialog">
      <v-row>
        <v-col><span class="font-weight-medium">Реквизиты</span><span class="ml-5">{{ entity.profile.partnerName }}</span></v-col>
      </v-row>
      <v-row v-if="entity.profile.profileType === 'LEGAL_ENTITY'">
        <v-col cols="12">
          <v-text-field
            v-model="form.profile.requisitesData.customerName"
            label="Заказчик"
            placeholder="Заказчик"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12">
          <v-text-field
            v-model="form.profile.requisitesData.legalAddress"
            label="Юридический адрес"
            placeholder="Юридический адрес"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12">
          <v-text-field
            v-model="form.profile.requisitesData.postAddress"
            label="Почтовый адрес"
            placeholder="Почтовый адрес"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="form.profile.requisitesData.inn"
            label="ИНН"
            placeholder="ИНН"
            outlined
            v-mask="'###############'"
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="form.profile.requisitesData.bik"
            label="БИК"
            placeholder="БИК"
            outlined
            v-mask="'###############'"
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="form.profile.requisitesData.paymentAccount"
            label="Р/С"
            placeholder="Р/С"
            outlined
            v-mask="'######################'"
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="form.profile.requisitesData.kpp"
            label="КПП"
            placeholder="КПП"
            outlined
            v-mask="'################'"
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="form.profile.requisitesData.bankName"
            label="Банк"
            placeholder="Банк"
            outlined
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="form.profile.requisitesData.correcpondentAccount"
            label="К/С"
            placeholder="К/С"
            outlined
            v-mask="'######################'"
            append-icon="mdi-pencil-outline"
            :rules="[required]"
          />
        </v-col>
      </v-row>
      <div v-else>
        <v-row>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.profile.requisitesData.cardNumber"
              label="Номер банковской карты"
              placeholder="Номер банковской карты"
              outlined
              v-mask="'#### #### #### ####'"
              append-icon="mdi-pencil-outline"
              :rules="[required]"
            />
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.profile.requisitesData.wallet"
              label="Номер электронного кошелька"
              placeholder="Номер электронного кошелька"
              outlined
              append-icon="mdi-pencil-outline"
              :rules="[required]"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.profile.requisitesData.bankName"
              label="Название банка"
              placeholder="Название банка"
              outlined
              append-icon="mdi-pencil-outline"
              :rules="[required]"
            />
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.profile.requisitesData.walletName"
              label="Название кошелька"
              placeholder="Название кошелька"
              outlined
              append-icon="mdi-pencil-outline"
              :rules="[required]"
            />
          </v-col>
        </v-row>
      </div>
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
  name: 'RequisitesEditDialog',
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
      required: v => !!v || 'Это поле необходимо заполнить'
    }
  },
  methods: {
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
