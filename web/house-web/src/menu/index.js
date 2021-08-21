import { uniqueId } from 'lodash'

/**
 * @description 给菜单数据补充上 path 字段
 * @param {Array} menu 原始的菜单数据
 */
function supplementPath (menu) {
  return menu.map(e => ({
    ...e,
    path: e.path || uniqueId('d2-menu-empty-'),
    ...e.children ? {
      children: supplementPath(e.children)
    } : {}
  }))
}

export const menuHeader = supplementPath([
  { path: '/index', title: '首页', icon: 'home' },
 
  {
    title: '系统管理',
    icon: 'folder-o',
    path: '/system',
    children: [
        { path: '/userMange', title: '用户管理' },
        { path: '/roleMange', title: '角色管理' },
        { path: '/menuMange', title: '菜单管理' }
    ]
  },
  {
    title: '房屋管理',
    icon: 'folder-o',
    path: '/house',
    children: [
      { path: '/houseMange', title: '出租屋管理' },
      { path: '/evaluateMange', title: '评价管理' }
    ]
  },
])

export const menuAside = supplementPath([
  { path: '/index', title: '首页', icon: 'home' },
  {
    title: '系统管理',
    icon: 'folder-o',
    path: '/system',
    children: [
        { path: '/userMange', title: '用户管理' },
        { path: '/roleMange', title: '角色管理' },
        { path: '/menuMange', title: '菜单管理' }
    ]
  },
  {
    title: '房屋管理',
    icon: 'folder-o',
    path: '/house',
    children: [
      { path: '/houseMange', title: '出租屋管理' },
      { path: '/evaluateMange', title: '评价管理' }
    ]
  },
])
