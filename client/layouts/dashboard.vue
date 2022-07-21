<template>
  <v-app>
    <TheHeader />
    <v-main>
      <v-container class="py-10">
        <Nuxt />
      </v-container>
    </v-main>
    <v-footer color="light-grey" :absolute="true" app>
      <TheFooter />
    </v-footer>
  </v-app>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DashboardLayout',
  middleware: 'auth',
  created() {
    const token = localStorage.getItem('token')
    this.$store.commit('user/setToken', token)
    this.$axios.defaults.headers.common = {
      Authorization: `Bearer ${token}`
    }
    this.$store.dispatch('user/getUser')
  }
})
</script>

<style scoped lang="scss">
::v-deep .v-main {
  background: var(--v-light-grey-base);
}

::v-deep .v-main__wrap {
  background: var(--v-white-base);
  border-radius: 40px 40px 0 0;
}
</style>
