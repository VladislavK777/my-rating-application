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
      } else if (store.getters['user/getUserRole'] === 'PARTNER') {
        redirect('/dashboard/partner/requests');
      } else {
        redirect('/dashboard/account/auth');
      }
    },
  ],
})
</script>

<style scoped lang="scss">

</style>
