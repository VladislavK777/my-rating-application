<template>
  <v-app>
    <v-container>
      <v-row>
        <v-col cols="12" sm="6">
          <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг">
        </v-col>
        <v-col cols="12" sm="6">
          <span class="error__credit">Проверить кредитный рейтинг</span>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col v-if="error.statusCode === 500 && error.message.includes('404')" class="error-container">
          <h2 class="error__title">Данный отчет более недоступен или была использована неправильная ссылка</h2>
          <span class="error__hint">Нажмите на кнопку ниже, чтобы перейти на главную страницу</span>
          <v-btn color="primary" height="73" width="260" to="/">На главную</v-btn>
        </v-col>
        <v-col v-else-if="error.statusCode === 404" class="error-container">
          <v-img :src="require(`~/assets/404.svg`)" alt="404" max-height="268" />
          <h2 class="error__title">К сожалению, такой страницы не существует</h2>
          <span class="error__hint">Нажмите на кнопку ниже, чтобы перейти на главную страницу</span>
          <v-btn color="primary" height="73" width="260" to="/">На главную</v-btn>
        </v-col>
        <v-col v-else class="error-container">
          <h2 class="error__title">Произошла ошибка, попробуйте позже</h2>
          <span class="error__hint">Нажмите на кнопку ниже, чтобы перейти на главную страницу</span>
          <v-btn color="primary" height="73" width="260" to="/">На главную</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: 'EmptyLayout',
  layout: 'empty',
  props: {
    error: {
      type: Object,
      default: null,
    },
  },
  data() {
    return {
      report: false,
      pageNotFound: '404 Данной страницы не существует',
      reportError: 'Данный отчет более недоступен или ссылка неправильная',
      otherError: 'Произошла ошибка, попробуйте позже',
    }
  },
  head() {
    const title =
      (this.error.statusCode === 500 && this.error.message.includes('404')) ? this.reportError : this.error.statusCode === 404 ? this.pageNotFound : this.otherError
    return {
      title,
    }
  },
}
</script>

<style scoped lang="scss">
.error {
  &-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  &__header {
    display: flex;
    justify-content: space-between;
  }

  &__credit {
    cursor: pointer;
    padding-bottom: 5px;
    font-size: 20px;
    border-bottom: 1px solid var(--v-black-base);
    transition: all .2s;

    &:hover {
      color: var(--v-primary-base);
      border-bottom: 1px solid var(--v-primary-base);
    }
  }

  &__title {
    margin-bottom: 20px;
    font-weight: 700;
    font-size: 40px;
  }

  &__hint {
    margin-bottom: 40px;
    font-size: 20px;
  }
}

::v-deep .v-application--wrap {
  min-height: fit-content !important;
}
</style>
