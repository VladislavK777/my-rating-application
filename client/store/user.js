export const state = () => ({
  user: {},
  role: null,
  token: null,
})

export const getters = {
  getUser(state) {
    return state.user
  },
  getUserEmail(state) {
    return state.user.email
  },
  getUserRole(state) {
    return state.role
  },
  getToken(state) {
    return state.token
  },
}

export const mutations = {
  setUser(state, user) {
    state.user = user
  },
  setRole(state, role) {
    localStorage.setItem('role', role)
    state.role = role
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
    await dispatch('getUser')
  },
  async getUser({ commit }) {
    const user = await this.$axios.$get('api/account')
    if (user.authorities.find((role) => role === 'ROLE_ADMIN')) {
      commit('setRole', 'ADMIN')
    } else {
      commit('setRole', 'PARTNER')
    }
    commit('setUser', user)
  },
  async adminChange({ dispatch }, payload) {
    await this.$axios.$post('api/account/admin', payload)
    await dispatch('getUser')
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
    commit('setRole', null)
    localStorage.removeItem('role')
    commit('setUser', {})
    delete this.$axios.defaults.headers.common.Authorization
  },
}
