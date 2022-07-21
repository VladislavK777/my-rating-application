<template>
  <div class="mb-10">
    <div v-if="userRole === 'ADMIN'" class="dashboard-nav-container"
         :class="{ 'dashboard-nav-container--mobile': $vuetify.breakpoint.mobile }">
      <NuxtLink to="/dashboard/partners" class="dashboard-nav__link" active-class="dashboard-nav__link--active">
        Партнеры
      </NuxtLink>
      <NuxtLink to="/dashboard/requests" class="dashboard-nav__link" active-class="dashboard-nav__link--active">
        Запросы
      </NuxtLink>
      <NuxtLink to="/dashboard/register" class="dashboard-nav__link" active-class="dashboard-nav__link--active">
        Регистрация юридического лица
      </NuxtLink>
      <NuxtLink to="/dashboard/register" class="dashboard-nav__link" active-class="dashboard-nav__link--active">
        Регистрация физического лица
      </NuxtLink>
      <NuxtLink to="/dashboard/payments" class="dashboard-nav__link" active-class="dashboard-nav__link--active">
        Выплаты партнерам
      </NuxtLink>
    </div>
    <div v-else class="dashboard-nav-container"
         :class="{ 'dashboard-nav-container--mobile': $vuetify.breakpoint.mobile }">
      <NuxtLink to="/dashboard/requests" class="dashboard-nav__link" active-class="dashboard-nav__link--active">
        Запросы
      </NuxtLink>
      <NuxtLink to="/dashboard/payments" class="dashboard-nav__link" active-class="dashboard-nav__link--active">
        Выплаты
      </NuxtLink>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DashboardNav',
  computed: {
    userRole() {
      return this.$store.getters['user/getUserRole']
    }
  }
})
</script>

<style scoped lang="scss">
.dashboard-nav {
  &-container {
    display: flex;
    gap: 80px;

    &--mobile {
      flex-direction: column;
      gap: 20px;
    }
  }

  &__link {
    color: var(--v-black-base);
    font-size: 18px;
    font-weight: 500;
    text-decoration: none;
    transition: color .2s;

    @media only screen and (max-width: 959px) {
      width: fit-content;
      font-size: 16px;
    }

    @media only screen and (max-width: 599px) {
      width: fit-content;
      font-size: 15px;
    }

    &:hover {
      color: var(--v-primary-base);
    }

    &--active {
      color: var(--v-black-base) !important;
      border-bottom: 1px solid var(--v-black-base);
    }
  }
}
</style>
