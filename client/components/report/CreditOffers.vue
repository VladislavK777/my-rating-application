<template>
  <div class="container">
    <h2 class="heading">Лучшие кредитные предложения</h2>
    <v-simple-table class="table">
      <template v-slot:default>
        <tbody v-if="!$vuetify.breakpoint.xs">
        <tr
          v-for="offer in creditOffers"
          :key="offer.name"
        >
          <td><img :src="require(`~/assets/logos/${offer.icon}`)" :alt="offer.name" class="table__cell"/></td>
          <td v-if="$vuetify.breakpoint.mdAndUp">{{ offer.name }}</td>
          <td>{{ offer.rate }}</td>
          <td>{{ offer.max }}</td>
          <td>{{ offer.duration }}</td>
          <td class="table__link"><a :href="offer.link" class="link">Онлайн-заявка</a></td>
        </tr>
        </tbody>
        <tbody v-else style="text-align: center">
        <tr>
          <td v-for="icon in icons" :key="icon"><img :src="require(`~/assets/logos/${icon}`)" alt="offer" class="table__cell"/></td>
        </tr>
        <tr>
          <td v-for="(max, i) in maxs" :key="i">{{ max }}</td>
        </tr>
        <tr>
          <td v-for="(duration, i) in durations" :key="i">{{ duration }}</td>
        </tr>
        <tr>
          <td v-for="(link, i) in links" :key="i" class="table__link"><a :href="link" class="link">Онлайн-заявка</a></td>
        </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
export default {
  name: 'CreditOffers',
  data() {
    return {
      creditOffers: [
        {
          icon: 'psb.png',
          name: 'Потребительский кредит онлайн',
          rate: 'от 8,4 %',
          max: 'до 5 млн',
          duration: 'до 84 мес',
          link: '',
        },
        {
          icon: 'alfa.png',
          name: 'Целый год без %',
          rate: 'от 8,4 %',
          max: 'до 500 тыс.',
          duration: 'до 60 мес',
          link: '',
        },
        {
          icon: 'skb.png',
          name: 'Прайм выгодный',
          rate: 'от 8,4 %',
          max: 'до 5 млн',
          duration: 'до 60 мес',
          link: '',
        },
        {
          icon: 'vtb.png',
          name: 'Кредит наличными онлайн',
          rate: 'от 8,4 %',
          max: 'до 7 млн',
          duration: 'до 60 мес',
          link: '',
        },
        {
          icon: 'mts.png',
          name: 'MTS Cashback',
          rate: 'от 8,4 %',
          max: 'до 1 млн',
          duration: 'до 36 мес',
          link: '',
        },
      ],
    }
  },
  computed: {
    icons() {
      return Array.from(this.creditOffers, offer => offer.icon)
    },
    maxs() {
      return Array.from(this.creditOffers, offer => offer.max)
    },
    durations() {
      return Array.from(this.creditOffers, offer => offer.duration)
    },
    links() {
      return Array.from(this.creditOffers, offer => offer.link)
    }
  }
}
</script>

<style scoped lang="scss">
.table {
  border: 1px solid rgba(39, 39, 39, 0.15);
  border-radius: 8px;

  &__cell {
    padding: 16px;
  }

  &__link {
    width: 23%;
    text-align: center;
    font-weight: 500;
    font-size: 16px;
  }
}

.link {
  color: var(--v-black-base);
  text-decoration: none;
  transition: color .2s;

  &:hover {
    color: var(--v-primary-base);
  }
}
</style>
