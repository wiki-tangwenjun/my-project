import Vue from 'vue'
import Vuex from 'vuex'

import d2admin from './modules/d2admin'
import house from './modules/house'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    d2admin,
    house
  }
})
