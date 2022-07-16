<template>
  <div class="container">
    <h2 class="heading">Кредитный рейтинг</h2>
    <v-row>
      <v-col cols="12" md="5">
        <v-row>
          <v-col cols="12" sm="6" md="12" class="flex-container flex-container--main">
            <div class="container--white">
              <span class="person__name">{{ clientName }}</span>
              <span class="person__date">на {{ date }}</span>
            </div>
            <div class="rating-container container--white">
              <div class="chart-diagram-container">
                <v-progress-circular
                  :value="chartData.value"
                  :size="progressWidth"
                  :width="progressWidth / 10"
                  :color="chartData.color"
                ></v-progress-circular>
                <div class="chart-inner-text">
              <span class="chart-inner-text--main">
                {{ ratingValue }}
              </span>
                  <span class="chart-inner-text--sub">
                {{ currentText }}
              </span>
                </div>
              </div>
              <span class="rating__title">{{ chartData.title }}</span>
            </div>
          </v-col>
          <v-col v-if="$vuetify.breakpoint.mobile" cols="12" sm="6">
            <div class="container--white">
              <span class="rating__comment">{{ ratingComment }}</span>
            </div>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" md="7" class="flex-container">
        <CreditRatingInfo v-for="info in creditRatingInfos" v-bind="info" :key="info.color" />
      </v-col>
    </v-row>
    <v-row v-if="!$vuetify.breakpoint.mobile">
      <v-col>
        <div class="container--white">
          <span>{{ ratingComment }}</span>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import CreditRatingInfo from '@/components/report/CreditRatingInfo'
import { getChartColorByValue, getDataByTitle, getRatingTitleByValue } from '@/helpers/helpers'

export default {
  name: 'CreditRating',
  components: { CreditRatingInfo },
  props: {
    ratingData: {
      type: [Object, Array],
      required: true,
    },
  },
  data() {
    return {
      clientName: '',
      date: '',
      ratingValue: '',
      ratingComment: '',
      creditRatingInfos: [
        {
          color: '#9AE023',
          range: 'Более 900. ',
          title: 'Максимальный балл',
          text: 'Максимально возможный балл. Идеальный клиент',
        },
        {
          color: '#C6F675',
          range: '750-900. ',
          title: 'Высокий балл',
          text: 'Положительные факторы. Хороший клиент',
        },
        {
          color: '#FFEB43',
          range: '650-750. ',
          title: 'Средний балл',
          text: 'Небольшие негативные факторы, есть вероятность отказа',
        },
        {
          color: '#FFC644',
          range: '450-650. ',
          title: 'Низкий балл',
          text: 'Серьёзные негативные факторы, высокая вероятность отказа',
        },
        {
          color: '#FF3C5F',
          range: 'Менее 450. ',
          title: 'Критично низкий',
          text: 'Критичные негативные факторы, высокая вероятность отказа',
        },
      ],
      window: {
        height: 0,
        width: 0,
      },
    }
  },
  computed: {
    progressWidth() {
      /* return this.window.width * 0.151 || 100 */
      return this.$vuetify.breakpoint.mobile ? 160 : 290
    },
    chartData() {
      return {
        value: Math.ceil(this.ratingValue / 9),
        color: getChartColorByValue(this.ratingValue),
        title: getRatingTitleByValue(this.ratingValue),
      }
    },
    currentText() {
      let num = this.ratingValue
      const words = ['балл', 'балла', 'баллов']
      if (num > 100) {
        num = num % 100
      }
      if (num <= 20 && num >= 10) {
        return words[2]
      }
      if (num > 20) {
        num = num % 10
      }
      return (num === 1 ? words[0] : num > 1 && num < 5 ? words[1] : words[2])
    },
  },
  created() {
    this.clientName = getDataByTitle(this.ratingData, 'clientName').text
    this.date = new Date().toLocaleDateString('ru-RU', {
      year: 'numeric', month: 'long', day: 'numeric',
    })
    this.ratingValue = getDataByTitle(this.ratingData, 'ratingVale').text
    this.ratingComment = getDataByTitle(this.ratingData, 'ratingComment').text
  },
  mounted() {
    window.addEventListener('resize', this.handleResize)
    this.handleResize()
  },
  destroyed() {
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize() {
      this.window.width = window.innerWidth
      this.window.height = window.innerHeight
    },
  },
}
</script>

<style scoped lang="scss">
.person {
  &__name {
    display: block;
    margin-bottom: 10px;
    font-weight: 600;
    font-size: 24px;

    @media only screen and (max-width: 959px) {
      font-size: 20px;
    }

    @media only screen and (max-width: 599px) {
      font-size: 18px;
    }
  }

  &__date {
    font-size: 20px;

    @media only screen and (max-width: 959px) {
      font-size: 16px;
    }

    @media only screen and (max-width: 599px) {
      font-size: 15px;
    }
  }
}

.rating {
  &-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 46px 110px !important;

    @media only screen and (max-width: 959px) {
      padding: 30px 86px !important;
    }

    @media only screen and (max-width: 599px) {
      padding: 20px 64px !important;
    }
  }

  &__title {
    margin-top: 20px;
    font-weight: 500;
    font-size: 24px;
    text-align: center;

    @media only screen and (max-width: 959px) {
      font-size: 16px;
    }

    @media only screen and (max-width: 599px) {
      font-size: 16px;
    }
  }

  &__comment {
    font-size: 20px;

    @media only screen and (max-width: 959px) {
      font-size: 16px;
    }

    @media only screen and (max-width: 599px) {
      font-size: 15px;
    }
  }
}

.chart {
  &-diagram {
    height: 200px;
    width: 200px;
    align-self: center;
    justify-self: center;
    z-index: 10;

    &-container {
      flex-grow: 1;
      position: relative;
      display: grid;
      place-content: center;
    }
  }

  &-inner-text {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;

    &--main {
      display: block;
      font-weight: 700;
      font-size: 66px;
      line-height: 80px;

      @media only screen and (max-width: 959px) {
        font-size: 20px;
        line-height: 28px;
      }

      @media only screen and (max-width: 599px) {
        font-size: 20px;
        line-height: 28px;
      }
    }

    &--sub {
      font-size: 20px;
      opacity: 0.5;

      @media only screen and (max-width: 959px) {
        font-size: 15px;
      }

      @media only screen and (max-width: 599px) {
        font-size: 15px;
      }
    }
  }
}

.flex-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>
