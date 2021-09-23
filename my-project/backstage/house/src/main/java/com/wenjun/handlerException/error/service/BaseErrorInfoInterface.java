package com.wenjun.handlerException.error.service;

/**
 * @projectName: my-project
 * @package: com.study.error
 * @className: BaseErrorInfoInterface
 * @author: tang wen jun
 * @description: 全局异常处理接口
 * @date: 2021年05月11日 16:43
 * @version: 1.0
 */
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     *
     * @return java.lang.String
     * @author tang wen jun
     * @date 2021/5/11 16:44
     */
    String getResultCode();

    /**
     * 错误描述
     *
     * @return java.lang.String
     * @author tang wen jun
     * @date 2021/5/11 16:45
     */
    String getResultMsg();
}
