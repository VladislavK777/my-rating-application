<template>
  <v-btn color="black" outlined height="40" @click="downloadCsv">Скачать в CSV
    <v-icon color="black" class="ml-2">mdi-tray-arrow-down</v-icon>
  </v-btn>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'ButtonCsv',
  props: {
    apiUrl: {
      type: String,
      required: true
    },
    fileName: {
      type: String,
      required: true
    }
  },
  methods: {
    async downloadCsv() {
      const data = await this.$axios.$get(this.apiUrl, { responseType: 'blob' })
      await this.generateLink(data)
    },
    generateLink(response) {
      const url = URL.createObjectURL(new Blob([response], { type: 'application/vnd.ms-excel' }))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', this.fileName)
      document.body.appendChild(link)
      link.click()
      link.remove()
    }
  }
})
</script>

<style scoped lang="scss">

</style>
