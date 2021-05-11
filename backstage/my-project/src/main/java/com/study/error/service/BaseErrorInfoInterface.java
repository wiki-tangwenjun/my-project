package com.study.error.service;

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
    * @description: 错误码
    * @author tang wen jun
    * @return java.lang.String
    * @date 2021/5/11 16:44
    */
    String getResultCode();

    /**
    * @description: 错误描述
    * @author tang wen jun
    * @return java.lang.String
    * @date 2021/5/11 16:45
    */
    String getResultMsg();
}
