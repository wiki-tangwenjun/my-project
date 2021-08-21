import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

/**
 * 在主框架内显示
 */
const frameIn = [
  {
    path: '/',
    redirect: { name: 'index' },
    component: layoutHeaderAside,
    children: [
      // 首页
      {
        path: 'index',
        name: 'index',
        meta: {
          auth: true
        },
        component: _import('system/index')
      },
      // 系统管理页面
      {
        path: '/userMange',
        name: 'userManage',
        meta: {
          title: '用户管理',
          auth: true
        },
        component: _import('systemManager/userManager')
      },
      {
        path: '/roleMange',
        name: 'roleManage',
        meta: {
          title: '角色管理',
          auth: true
        },
        component: _import('systemManager/roleManager')
      },
      {
        path: '/menuMange',
        name: 'menuManage',
        meta: {
          title: '菜单管理',
          auth: true
        },
        component: _import('systemManager/menuManager')
      },
      // 出租屋
      {
        path: '/houseMange',
        name: 'house',
        meta: {
          title: '出租屋管理',
          auth: true
        },
        component: _import('houseManager/house')
      },
      // 评价
      {
        path: '/evaluateMange',
        name: 'evaluate',
        meta: {
          title: '评价管理',
          auth: true
        },
        component: _import('houseManager/evaluate')
      },
      // 403 无权限
      {
        path: '/403',
        name: '403',
        meta: {
          title: '无权限',
          auth: true
        },
        component: _import('error/403')
      },
      // 系统 前端日志
    //   {
    //     path: 'log',
    //     name: 'log',
    //     meta: {
    //       title: '前端日志',
    //       auth: true
    //     },
    //     component: _import('system/log')
    //   },
      // 刷新页面 必须保留
      {
        path: 'refresh',
        name: 'refresh',
        hidden: true,
        component: _import('system/function/refresh')
      },
      // 页面重定向 必须保留
      {
        path: 'redirect/:route*',
        name: 'redirect',
        hidden: true,
        component: _import('system/function/redirect')
      }
    ]
  }
]

/**
 * 在主框架之外显示
 */
const frameOut = [
  // 登录
  {
    path: '/login',
    name: 'login',
    component: _import('system/login')
  }
]

/**
 * 错误页面
 */
const errorPage = [
  {
    path: '*',
    name: '404',
    component: _import('system/error/404')
  }
]

// 导出需要显示菜单的
export const frameInRoutes = frameIn

// 重新组织后导出
export default [
  ...frameIn,
  ...frameOut,
  ...errorPage
]
