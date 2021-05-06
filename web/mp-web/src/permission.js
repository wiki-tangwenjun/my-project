import router from './router'
import CookieUtil from '@/utils/cookie.js'
import store from './store'
import { Message } from 'element-ui'

const whiteList = ['/login'] // 去登录页面无需校验权限

router.beforeEach(async (to, from, next) => {
    CookieUtil.setToken();
    const hasToken = CookieUtil.getToken();
    if (hasToken) {
       to.path == '/login' ? next('/index') : next();
    } else {
        next('/');
    }
});

router.afterEach(() => {

})
