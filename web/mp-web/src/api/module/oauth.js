import Request from '@../service'

export default class OauthService {

    /**
     * @access_token {删除密码} access_token
     * @oldPassword {旧密码} oldPassword
     * @newPassword {删除密码} newPassword
     */
    updatePassword(param) {
        return Request({
            url: '/oauth/user/updatePassword',
            method: 'put',
            params: param
        });
    }
}
