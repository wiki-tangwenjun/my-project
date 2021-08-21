import api from '@/api'

export default {
  namespaced: true,
  actions: {
    /**
     * @description 获取验证码
     * @param {Object} context
     */
    getVerificationCode () {
      return new Promise((resolve, reject) => {
        api.getVerificationCode().then(response => {
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    /**
     * @description 获取角色权限
     * @param {Object} context
     */
    getUserRoleRights () {
        return new Promise((resolve, reject) => {
            api.getUserRoleRights().then(response => {
              resolve(response)
            }).catch(error => {
              reject(error)
            })
        })
    },
  }
}
