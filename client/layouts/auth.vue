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
  name: 'AuthLayout'
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
