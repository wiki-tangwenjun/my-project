package com.wenjun.util;

import com.wenjun.handlerException.error.CommonEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @projectName: house
 * @package: com.wenjun.util
 * @className: ServletHttpRequest
 * @author: tang wen jun
 * @description: 获取request工具类
 * @date: 2021年08月07日 1:08
 * @version: 1.0
 */
public class ServletHttpRequest {

    /**
     * 获取token
     * @param request HttpServletRequest对象
     * @return String
     * @throws Exception 没获取到就是token异常
     */
    public static String getHttpServletRequest(HttpServletRequest request) throws Exception {
        String header = request.getHeader("Authorization");
        if (CheckUtil.isNull(header) && !header.startsWith("Bearer ")) {
            throw new Exception(CommonEnum.ERROR_TOKEN.getDescription());
        }

        return header.substring(7);
    }

}
