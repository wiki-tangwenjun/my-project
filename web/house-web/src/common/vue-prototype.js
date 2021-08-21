import router from "@/router";
import util from '@/libs/util.js'

/** 获取当前角色是否有某个页面的按钮 */
export function $isHaveBtn(btn) {
    return router.meta.operations.includes(btn);
}
/** 获取当前角色是否有某个页面 */
export function $isHavePage(page) {
    let f = false;
    const token = util.cookies.get('token');
    const userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
    if (token && token !== 'undefined' && userInfo && userInfo !== 'undefined') {
        let roleMenu = userInfo.userRole.roleMenu;
        for (let i = 0; i < roleMenu.lengthi++; i++) {
            if (roleMenu[i][menuHref] == page) {
                f = true;
            }
        }

        if (!f) {
            router.replace({name: '403'});
        }

        return true;
    } else {
        router.replace("/login");
    }
}