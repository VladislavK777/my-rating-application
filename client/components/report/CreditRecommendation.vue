<template>
  <div class="container--white recommendation-container">
    <span class="recommendation__number">{{ `0${index + 1}` }}</span>
    <div>
      <span class="recommendation__title">{{ titleString }}</span>
      <p class="recommendation__text" v-html="text"></p>
    </div>
  </div>
</template>

<script>
const titles = [
  { title: 'emptyHistory', string: 'Пустая кредитная история' },
  { title: 'ratingComment', string: 'Кредитная история' },
  { title: 'activeDebt', string: 'Просрочка по активным кредитам' },
  { title: 'closeDebt', string: 'Просрочка в истории закрытых кредитов' },
  { title: 'mfoCredit', string: 'Займы в МФО' },
  { title: 'requestCredit', string: 'Запросы' },
  { title: 'creditLoad', string: 'Нагрузка активными кредитами и поручительствам' },
]

export default {
  name: 'CreditRecommendation',
  props: {
    index: {
      type: Number,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    text: {
      type: String,
      required: true,
    },
  },
  computed: {
    titleString() {
      return titles.find(t => t.title === this.title).string
    },
  },
}
</script>

<style scoped lang="scss">
.recommendation {
  &-container {
    display: flex;
    gap: 16px;
  }

  &__number {
    font-weight: 500;
    font-size: 22px;
  }

  &__title {
    display: block;
    margin-bottom: 10px;
    font-weight: 500;
    font-size: 20px;
  }

  &__text {
    margin: 0;
    font-size: 20px;
    white-space: pre-line;
  }
}
</style>
