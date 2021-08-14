import Vue from 'vue'
import VueRouter from 'vue-router'

// 进度条
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

import store from '@/store/index'

import util from '@/libs/util.js'

// 路由数据
import routes from './routes'

Vue.use(VueRouter)

// 导出路由 在 main.js 里使用
const router = new VueRouter({
  routes
})

/**
 * 路由拦截
 * 权限验证
 */
router.beforeEach((to, from, next) => {
    // 进度条
    NProgress.start()
    // 关闭搜索面板
    store.commit('d2admin/search/set', false)
    // 获取路由的meta信息，判断当前页面是否需要验证（登录验证，权限验证等等）
    // 验证当前路由所有的匹配中是否需要有登录验证的
    if (to.matched.some(r => r.meta.auth)) {
        // 这里暂时将cookie里是否存有token作为验证是否登录的条件
        // 请根据自身业务需要修改
        const token = util.cookies.get('token'); // 获取cookie中的token
        const userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
        if (token && token !== 'undefined' && userInfo && userInfo !== 'undefined') {
            let roleMenu = userInfo.userRole;
            // set权限去重
            let set = new Set();
            // 取出角色权限
            for (let i = 0; i < roleMenu.length; i++) {
                for (let k = 0; k < roleMenu[i].roleMenu.length; k++) {
                    set.add(roleMenu[i].roleMenu[k]);
                }
            }
            // 登录用户的所有权限
            let permissions = Array.from(set);

            let allow = false;
            // 登录和无权限页面不验证权限 直接跳过
            if (to.path == '/index' || to.path == '/403') {
                next();
                return;
            }
            // 非跳转首页路由则进行权限认证
            if (to.path !== '/index' || to.path !== '/403') {
            // 取出角色路由拥有页面的权限参数
                let pagePermissions = [];
                for (let i = 0; i < permissions.length; i++) {
                    if (permissions[i].menuRoute.indexOf(to.fullPath) != -1) {
                        pagePermissions.push(permissions[i].permissionName);
                    }
                }
                
                for (let i = 0; i < permissions.length; i++) {
                    if (permissions[i].menuHref && permissions[i].menuHref !== null) {
                        let item = router.match(permissions[i].menuHref);  // 找到权限列表中匹配改条路由对应的权限信息
                        if (item) {
                            // 匹配路由=> 完全匹配或者前缀匹配
                            if (permissions[i].menuHref === to.path || to.path.startsWith(permissions[i].menuHref)) {
                                // 匹配到路由后，将权限信息添加在路由的meta中
                                item.meta.operations = pagePermissions.join(",");
                                allow = true;
                            }
                        }
                    }
                }
            }

            // 根据allow判断是否可以跳转到下一个页面
            if (allow) {
                next()
            } else {
                next({ name: '401' })
            }
        } else {
            // 没有登录的时候跳转到登录界面
            // 携带上登陆成功之后需要跳转的页面完整路径
            next({
                name: 'login',
                query: {
                    redirect: to.fullPath
                }
            })
        }
    } else {
        // 不需要身份校验 直接通过
        next()
    }
})

router.afterEach(to => {
  // 进度条
  NProgress.done()
  // 多页控制 打开新的页面
  store.dispatch('d2admin/page/open', to)
  // 更改标题
  util.title(to.meta.title)
})

export default router