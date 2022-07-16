<template>
  <v-app>
    <v-main>
      <v-container class="pt-3 pb-10">
        <Nuxt />
      </v-container>
    </v-main>
    <v-footer color="light-grey" padless :absolute="!fixed" app>
      <TheFooter />
    </v-footer>
  </v-app>
</template>

<script>
export default {
  name: 'DefaultLayout',
  data() {
    return {
      clipped: false,
      drawer: false,
      fixed: false,
      items: [
        {
          icon: 'mdi-apps',
          title: 'Welcome',
          to: '/',
        },
        {
          icon: 'mdi-chart-bubble',
          title: 'Inspire',
          to: '/inspire',
        },
      ],
      miniVariant: false,
      right: true,
      rightDrawer: false,
      title: 'Vuetify.js',
    }
  },
}
</script>

<style scoped lang="scss">
</style>
