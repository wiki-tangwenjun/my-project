import CookieUtil from '@/utils/cookie.js'
// import UserService from '@/api/userInfo'

// this.$store.state.userInfo
const state = {
  userInfo: null,
}

// 以属性方式访问，作为Vue的响应式系统的一部分缓存其中
// this.$store.getters['userInfo/userInfo']
// this.$store.getters['userInfo/userInfo'].username;
// 如下返回的是函数,不会缓存结果
// ...mapGetters('userInfo/userInfo')
// ...mapGetters('userInfo', [userInfo])
const getters = {
  userInfo: state => {
    if (!state.userInfo) {
      return CookieUtil.getUserInfo();
    }
    return state.userInfo;
  }
};

const mutations = {
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo;
    CookieUtil.setUserInfo(userInfo);
  },
}

const actions = {
  // 获取用户信息
  getUserInfoReq({ commit }) {
    return new Promise((resolve, reject) => {
    //   new UserService().getUserInfo().then((res) => {
    //     console.log('getUserInfoReq:', res);
    //     commit('SET_USER_INFO', res.value);
    //     resolve(res.value);
    //   }).catch((error) => {
    //     console.log(error)
    //     reject(error)
    //   })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}