<template>
  <v-app>
    <TheHeader @show-drawer="drawer = true" />
    <v-main>
      <v-container class="py-10">
        <DashboardNav v-if="!$vuetify.breakpoint.mobile" />
        <Nuxt />
      </v-container>
    </v-main>
    <DashboardDrawer v-model="drawer" v-if="$vuetify.breakpoint.mobile" />
    <v-footer color="light-grey" :absolute="true" app id="footer">
      <TheFooter />
    </v-footer>
  </v-app>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import DashboardNav from '../components/dashboard/DashboardNav.vue'
import DashboardDrawer from '../components/dashboard/DashboardDrawer.vue'

export default defineComponent({
  name: 'PartnerLayout',
  components: { DashboardDrawer, DashboardNav },
  middleware: ['auth', 'partner'],
  data() {
    return {
      drawer: false
    }
  },
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
