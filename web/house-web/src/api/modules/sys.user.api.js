export default ({ service, request, faker, tools }) => ({
  /**
   * @description 登录
   * @param {Object} data 登录携带的信息
   */
  login (data = {}) {
    // 接口请求
    return request({
      url: 'user/login',
      method: 'get',
      data
    })
  },

  // 获取验证码
  getVerificationCode (data = {}) {
    return request({
      url: 'user/getVerificationCode',
      method: 'get',
      data
    })
  }
})
