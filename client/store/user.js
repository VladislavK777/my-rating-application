export const state = () => ({
  user: {},
  token: null,
})

export const mutations = {
  setUser(state, user) {
    state.user = user
  },
  setToken(state, token) {
    state.token = token
  },
}

export const actions = {
  async login({ commit, dispatch }, payload) {
    const data = await this.$axios.$post('api/authenticate', payload)
    commit('setToken', data.id_token)
    localStorage.setItem('token', data.id_token)
    this.$axios.defaults.headers.common = {
      Authorization: `Bearer ${data.id_token}`,
    }
    dispatch('getUser')
  },
  async getUser({ commit }) {
    const user = await this.$axios.$get('api/account')
    commit('setUser', user)
  },
  async change(_, payload) {
    await this.$axios.$post('api/account/change-password', payload)
  },
  async remember(_, payload) {
    await this.$axios.$post('api/account/reset-password/init', payload)
  },
  async reset(_, payload) {
    await this.$axios.$post('api/account/reset-password/finish', payload)
  },
  async logout({ commit }) {
    await this.$axios.$get('api/authenticate')
    commit('setToken', null)
    localStorage.removeItem('token')
    commit('setUser', {})
    delete this.$axios.defaults.headers.common.Authorization
  },
}
