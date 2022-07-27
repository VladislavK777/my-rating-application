<template>
  <v-app-bar elevation="0" height="104" max-height="104" color="light-grey">
    <v-row
      v-if="userRole"
      no-gutters
      align="center"
      :class="{ 'pt-3': !$vuetify.breakpoint.xs, 'px-10': !$vuetify.breakpoint.xs }"
      :style="{ gap: $vuetify.breakpoint.xs ? '30px' : '50px' }"
    >
      <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг">
      <span v-if="userRole === 'ADMIN'">Административный интерфейс</span>
      <span v-else-if="!$vuetify.breakpoint.xs">Личный кабинет партнера</span>
      <v-spacer />
      <template v-if="!$vuetify.breakpoint.mobile">
        <div class="email-container">
          <span v-if="userRole === 'ADMIN'" class="email-container__admin" @click="goChangeAdmin">{{ userEmail }}</span>
          <span v-else>{{ userEmail }}</span>
          <span class="email-container__change" @click="goChange">изменить пароль</span>
        </div>
        <v-btn text @click="logout">Выйти</v-btn>
      </template>
      <template v-else>
        <v-btn icon @click="$emit('show-drawer')">
          <v-icon color="black">mdi-menu</v-icon>
        </v-btn>
      </template>
    </v-row>
    <v-row
      v-else
      no-gutters
      align="center"
      :class="{ 'pt-3': !$vuetify.breakpoint.xs, 'px-10': !$vuetify.breakpoint.xs }"
      :style="{ gap: $vuetify.breakpoint.xs ? '30px' : '50px' }"
    >
      <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг">
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
    goChangeAdmin() {
      this.$router.replace({ path: '/dashboard/account/admin' })
    },
    goChange() {
      this.$router.replace({ path: '/dashboard/account/change' })
    },
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

.email-container {
  display: flex;
  flex-direction: column;

  &__admin {
    color: #272727;
    transition: all .2s;

    &:hover {
      cursor: pointer;
      color: var(--v-primary-base);
    }
  }

  &__change {
    color: #272727;
    font-size: 14px;
    opacity: 0.5;
    transition: all .2s;

    &:hover {
      cursor: pointer;
      color: var(--v-primary-base);
      opacity: 1;
    }
  }
}
</style>
