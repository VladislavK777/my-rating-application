<template>
  <v-app-bar elevation="0" height="104" max-height="104" color="light-grey">
    <div class="pt-3 px-10">
      <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг">
    </div>
  </v-app-bar>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'TheHeader'
})
</script>

<style scoped lang="scss">
.dashboard-header {
  padding: 50px 60px 30px 60px;
}
</style>
