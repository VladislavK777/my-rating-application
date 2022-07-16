<template>
  <div>
    <img src="~/assets/logo.svg" height="16" alt="Мой рейтинг" class="logo">
    <div class="report-container">
      <CreditRating :rating-data="reportData.box1" />
      <CreditLoad :load-data="reportData.box2" />
      <CreditTypes :types-data="reportData.box3" />
      <CreditRecommendations :recommendations-data="reportData.box4" />
      <CreditOffers />
    </div>
  </div>
</template>

<script>
import CreditRating from '@/components/report/CreditRating'
import CreditLoad from '@/components/report/CreditLoad'
import CreditRecommendations from '@/components/report/CreditRecommendations'
import CreditOffers from '@/components/report/CreditOffers'
import CreditTypes from '@/components/report/CreditTypes'

export default {
  name: 'ReportPage',
  components: { CreditTypes, CreditOffers, CreditRecommendations, CreditLoad, CreditRating },
  async asyncData({ params, $axios }) {
    const reportData = await $axios.$get(`order/result/${params.id}`, {
      headers: {
        'X-API-Key': '4d7a8b90-7520-4b19-9c3e-36b8f253225d',
      },
    }).catch(err => {
      throw new Error('Report error ' + err)
    })
    return { reportData }
  },
}
</script>

<style scoped lang="scss">
.logo {
  margin-bottom: 60px;
}

.container {
  display: flex;
  flex-direction: column;
  gap: 40px;
  padding: 70px;
  background: var(--v-light-grey-base);
  border-radius: 32px;

  @media only screen and (max-width: 959px) {
    padding: 20px;
  }

  ::v-deep &--white {
    padding: 30px ;
    height: 100%;
    background: var(--v-white-base);
    border-radius: 32px;

    @media only screen and (max-width: 599px) {
      padding: 20px;
    }
  }
}

::v-deep .heading {
  font-weight: 700;
  font-size: 40px;

  @media only screen and (max-width: 959px) {
    font-size: 30px;
  }

  @media only screen and (max-width: 599px) {
    font-size: 20px !important;
  }
}

.report-container {
  display: flex;
  flex-direction: column;
  gap: 70px;
}
</style>
