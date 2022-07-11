<template>
  <div class="container">
    <h2 class="heading">Рекомендации по вашему кредитному рейтингу</h2>
    <CreditRecommendation
      v-for="(recommendation, i) in recommendationsData"
      :index="i"
      :title="recommendation.title"
      :text="recommendation.text"
      :key="i"
    />
  </div>
</template>

<script>
import CreditRecommendation from '@/components/report/CreditRecommendation'

export default {
  name: 'CreditRecommendations',
  components: { CreditRecommendation },
  props: {
    recommendationsData: {
      type: [Object, Array],
      required: true,
    },
  },
}
</script>

<style scoped lang="scss">

</style>
