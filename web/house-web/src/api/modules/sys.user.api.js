export default ({request}) => ({
  /**
   * @description 档案校验
   * @param {Object} param 文件夹
   */
  login(param = {}) {
    return request({
      url: `user/login`,
      method: 'get',
      params: param,
    })
  },

  // 获取验证码
  getVerificationCode () {
    return request({
      url: 'user/getVerificationCode',
      method: 'get'
    })
  }
})
