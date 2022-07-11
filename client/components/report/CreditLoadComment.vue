<template>
  <div class="container--white">
    <span class="comment__title">{{ titleString }}</span>
    <p class="comment__text" v-html="text"></p>
  </div>
</template>

<script>
const titles = [
  { title: 'emptyHistory', string: 'Пустая кредитная история' },
  { title: 'activeDebt', string: 'Просрочка по активным кредитам' },
  { title: 'closeDebt', string: 'Просрочка в истории закрытых кредитов' },
  { title: 'badDebt', string: 'Безнадёжный долг' },
  { title: 'mfoCredit', string: 'Займы в МФО' },
  { title: 'requestCredit', string: 'Запросы' },
  { title: 'creditLoad', string: 'Нагрузка активными кредитами и поручительствам' },
]

export default {
  name: 'CreditLoadComment',
  props: {
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
.comment {
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
