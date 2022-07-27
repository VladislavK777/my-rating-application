<template>
  <div></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DashboardPage',
  layout: 'dashboard',
  middleware: [
    function({ redirect }) {
      if (localStorage.getItem('role') === 'ADMIN') {
        redirect('/dashboard/partners');
      } else if (localStorage.getItem('role') === 'PARTNER') {
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
