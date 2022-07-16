<template>
  <div class="info-container" :style="{ background: color }">
    <div class="info-header">
      <span class="info__header-range">
        {{ range }}
      </span>
      <span class="info__header-title">
        {{ title }}
      </span>
    </div>
    <span class="info__text">
      {{ text }}
    </span>
  </div>
</template>

<script>
export default {
  name: 'CreditRatingInfo',
  props: {
    color: {
      type: String,
      required: true,
    },
    range: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    text: {
      type: String,
      required: true,
    }
  },
}
</script>

<style scoped lang="scss">
.info {
  &-container {
    padding: 24px 30px;
    border-radius: 32px;
  }

  &__header {
    &-range {
      font-weight: 500;
      font-size: 20px;

      @media only screen and (max-width: 599px) {
        font-size: 17px;
      }
    }

    &-title {
      font-size: 20px;

      @media only screen and (max-width: 599px) {
        font-size: 17px;
      }
    }
  }

  &__text {
    font-size: 16px;
    opacity: 0.5;

    @media only screen and (max-width: 599px) {
      font-size: 15px;
    }
  }
}
</style>
