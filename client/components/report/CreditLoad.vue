<template>
  <div class="container">
    <h2 class="heading">Кредитная нагрузка</h2>
    <div class="main-container">
      <div class="load-container">
        <span class="load__title">{{ loadData.load1.title }}</span>
        <div class="load">
          <v-img :src="require(`~/assets/load/load_green.svg`)" max-width="380" max-height="380" contain />
          <span class="load__value">{{ loadData.load1.text }}</span>
        </div>
      </div>
      <div class="load-container load-container--center">
        <span class="load__title">{{ loadData.load2.title }}</span>
        <div class="load">
          <v-img :src="require(`~/assets/load/load_grey.svg`)" max-width="480" max-height="480" contain />
          <span class="load__value">{{ loadData.load2.text }}</span>
        </div>
      </div>
      <div class="load-container">
        <span class="load__title">{{ loadData.load3.title }}</span>
        <div class="load">
          <v-img :src="require(`~/assets/load/load_red.svg`)" max-width="380" max-height="380" contain />
          <span class="load__value">{{ loadData.load3.text }}</span>
        </div>
      </div>
    </div>
    <CreditLoadComment
      v-for="comment in loadData.comments"
      :title="comment.title"
      :text="comment.text"
      :key="comment.title"
    />
  </div>
</template>

<script>
import CreditLoadComment from '@/components/report/CreditLoadComment'

export default {
  name: 'CreditLoad',
  components: { CreditLoadComment },
  props: {
    loadData: {
      type: Object,
      required: true,
    },
  },
}
</script>

<style scoped lang="scss">
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.load {
  position: relative;
  display: grid;
  place-content: center;

  &-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;
    z-index: 99;

    &--center {
      margin: 0 -65px;
      z-index: 90;
    }
  }

  &__title {
    width: 60%;
    font-weight: 600;
    font-size: 18px;
    text-align: center;
  }

  &__value {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
  }
}
</style>
