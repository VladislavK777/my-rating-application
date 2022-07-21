<template>
  <v-app-bar elevation="0" height="104" max-height="104" color="light-grey">
    <v-row class="pt-3 px-10" no-gutters align="center" :style="{ gap: $vuetify.breakpoint.mobile ? '30px' : '50px' }">
      <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг">
      <span v-if="userRole === 'ADMIN'">Административный интерфейс</span>
      <span v-else>Личный кабинет партнера</span>
      <v-spacer />
      <template v-if="!$vuetify.breakpoint.mobile">
        <span>{{ userEmail }}</span>
        <v-btn text @click="logout">Выйти</v-btn>
      </template>
      <template v-else>
        <v-btn icon @click="$emit('show-drawer')">
          <v-icon color="black">mdi-menu</v-icon>
        </v-btn>
      </template>
    </v-row>
  </v-app-bar>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'TheHeader',
  computed: {
    userRole() {
      return this.$store.getters['user/getUserRole']
    },
    userEmail() {
      return this.$store.getters['user/getUserEmail']
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('user/logout').then(() => {
        this.$router.push('/dashboard/account/auth')
      })
    }
  }
})
</script>

<style scoped lang="scss">
.dashboard-header {
  padding: 50px 60px 30px 60px;
}
</style>
