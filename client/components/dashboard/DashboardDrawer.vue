<template>
  <v-navigation-drawer
    :value="value"
    app
    color="white"
    right
    :width="$vuetify.breakpoint.xs ? '100%' : '400px'"
    @input="$emit('input', $event)"
    class="dashboard-drawer"
  >
    <div>
      <div class="dashboard-drawer__header">
        <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг">
        <v-btn v-if="$vuetify.breakpoint.xs" icon @click="$emit('input', false)"><v-icon>mdi-close</v-icon></v-btn>
      </div>
      <DashboardNav />
    </div>
    <div class="dashboard-drawer__footer">
      <span>{{ userEmail }}</span>
      <v-btn v-if="userRole === 'ADMIN'" text to="/dashboard/account/admin" width="fit-content">Изменить аккаунт</v-btn>
      <v-btn v-else text to="/dashboard/account/change" width="fit-content">Изменить пароль</v-btn>
      <v-btn text @click="logout" width="fit-content">Выйти</v-btn>
    </div>
  </v-navigation-drawer>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import DashboardNav from './DashboardNav.vue'

export default defineComponent({
  name: 'DashboardDrawer',
  components: { DashboardNav },
  props: {
    value: {
      type: Boolean,
      required: true
    }
  },
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
.dashboard-drawer {
  padding: 40px;

  ::v-deep .v-navigation-drawer__content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  &__header {
    margin-bottom: 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  &__footer {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
}
</style>
