<template>
  <div></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DashboardPage',
  layout: 'dashboard',
  middleware: [
    function({ store, redirect }) {
      if (store.getters['user/getUserRole'] === 'ADMIN') {
        redirect('/dashboard/partners');
      } else {
        redirect('/dashboard/partner/requests');
      }
    },
  ],
})
</script>

<style scoped lang="scss">

</style>
