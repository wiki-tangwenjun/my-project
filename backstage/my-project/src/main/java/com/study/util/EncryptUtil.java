package com.study.util;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtil {
    private static final String MD5_SALT = "twj12345";

    /**
     * md5加密
     *
     * @param content  加密内容
     * @param salt     加密盐值
     * @param iterator 迭代次数-加密次数
     * @return
     */
    static public String md5(String content, String salt, int iterator) {

        for (int index = 0; index < iterator; ++index) {
            if (CheckUtil.isNull(salt)) {
                salt = MD5_SALT;
            }
            content = DigestUtils.md5Hex(content + salt);
        }
        return content.toString();
    }
}
