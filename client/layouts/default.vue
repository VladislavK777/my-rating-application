<template>
  <v-app>
    <v-main>
      <v-container class="default-container pt-3 pb-10">
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
.default-container {
  max-width: 67%;

  @media only screen and (max-width: 959px) {
    max-width: 100%;
  }
}
</style>
