<template>
  <v-menu
    v-model="menu"
    :close-on-content-click="false"
    :nudge-right="40"
    transition="scale-transition"
    offset-y
    min-width="auto"
  >
    <template #activator="{ on, attrs }">
      <v-text-field
        :value="period[0] ? dateFormatted.join(' - ') : ''"
        color="black"
        outlined
        dense
        label="Выбрать период"
        hide-details
        append-icon="mdi-calendar"
        readonly
        clearable
        @click:clear="period = []; $emit('input', ['', '']); $emit('clear')"
        v-bind="attrs"
        v-on="on"
      ></v-text-field>
    </template>
    <v-date-picker
      v-model="period"
      color="black"
      range
      show-adjacent-months
      :first-day-of-week="1"
      locale="ru-RU"
      @input="$emit('input', $event)"
    ></v-date-picker>
  </v-menu>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DatePicker',
  props: {
    value: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      menu: false,
      period: []
    }
  },
  computed: {
    dateFormatted() {
      const dateFrom = this.period[0] ? new Intl.DateTimeFormat('ru', {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric'
      }).format(new Date(this.period[0])) : ''
      const dateTo = this.period[1] ? new Intl.DateTimeFormat('ru', {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric'
      }).format(new Date(this.period[1])) : ''
      return [dateFrom, dateTo]
    }
  }
})
</script>

<style scoped lang="scss">

</style>
