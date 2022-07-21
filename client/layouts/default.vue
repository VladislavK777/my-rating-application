<template>
  <v-app>
    <v-main>
      <v-container class="pt-3 pb-10">
        <Nuxt />
      </v-container>
    </v-main>
    <v-footer color="light-grey" :absolute="true" app>
      <TheFooter />
    </v-footer>
  </v-app>
</template>

<script>
export default {
  name: 'DefaultLayout',
}
</script>

<style scoped lang="scss">
</style>
