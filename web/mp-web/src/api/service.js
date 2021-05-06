import axios from 'axios'
import { Message } from 'element-ui'
import CookieUtil from '@/utils/cookie'
import router from '@/router/index.js'
import store from '@/store'

const { NODE_ENV } = process.env
const config = {
  development: '/api',
  production: '/',
}

const service = axios.create({
  baseURL: config[NODE_ENV],
  withCredentials: true,
  timeout: 10000
})

service.interceptors.request.use(
  config => {
    console.log(CookieUtil.getToken());
    if (!CookieUtil.getToken() || CookieUtil.getToken() === "undefined"){
      return config;
    }

    // 方式一：以自定义头方式添加令牌
    //config.headers.Authorization = "Bearer " + CookieUtil.getToken();

    // 方式二：以参数方式添加令牌
    config.url.indexOf("?")== -1 ? config.url = config.url + '?access_token=' + CookieUtil.getToken() : config.url = config.url + '&access_token=' + CookieUtil.getToken();

    return config;
  },
  error => {
    console.log('网络请求错误:', error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    console.log('response:', response)
    if (response.status !== 200) {
      Message({
        message: '网络通讯错误,错误码：' + response.status,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error('网络通讯错误,错误码：' + response.status))
    }

    if (response.config.url == '/api/oauth/user/findByToken') {
        if (response.data.error == -1) {
            this.$store.dispatch("./userInfo/logout").then(res => {
              this.$store.commit("regionInfo/RESET_TREEDATA");
              this.$router.push({ path: "/login" });
            });
        }
    }
    if (-1 == response.config.url.indexOf('oauth/token')) {
      if (response.data && response.data.error == 0){
        return Promise.resolve(response.data);
      } else {
        return Promise.reject(response.data);
      }
    }
    return response.data;
  },
  error => {
    console.log('error', error);
    if (error.config.url === 'oauth/token') {
      return Promise.reject(error)
    }

    // 刷新token失效或出现401未授权错误
    if (error.response.status == 401 || error.response.status == 400){
      Message({
        message: '权限错误或登录失效',
        type: 'error',
        duration: 5 * 1000
      });

      // store.dispatch('userInfo/logout').then(res=>{
      //   router.replace({ name: 'login' });
      // })
      store.commit('userInfo/RESET_TOKEN');
      router.replace({ name: 'login' });

      return Promise.reject(error);
    }

    Message({
      message: error.message || '未知的服务器回复错误',
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
)

export default service
