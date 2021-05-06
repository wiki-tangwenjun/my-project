import Cookies from 'js-cookie'

/**
 * cookie工具类
 */
export default class CookieUtil {
  static tokenKey = "token";
  /**
   * 从cookie中获取用户token
   */
  static getToken() {
    return Cookies.get(CookieUtil.tokenKey);
  }

  /**
   * 更新本地cookie
   * @param {*} token 
   */
  static setToken() {
    return Cookies.set(CookieUtil.tokenKey, '123');
  }
}
