package com.wenjun.handlerException;

import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import com.wenjun.handlerException.exception.HouseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @projectName: my-project
 * @package: com.study.handlerException
 * @className: GlobalExceptionHandler
 * @author: tang wen jun
 * @description: 全局异常处理器
 * @date: 2021年05月11日 17:30
 * @version: 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @param e 异常参数
     * @return com.study.error.ReturnValue<java.lang.String>
     * @description: 处理自定义的业务异常
     * @author tang wen jun
     * @date 2021/5/11 18:30
     */
    @ExceptionHandler(value = HouseException.class)
    @ResponseBody
    public ReturnValue<String> bizExceptionHandler(CommonEnum e) {
        log.error(e.getError(), e.getDescription());
        return new ReturnValue<>(e.getError(), e.getDescription());
    }

    /**
     * @description: 捕捉无权限操作失败异常
     * @author wen jun tang
     * @param ex 异常参数
     * @return com.wenjun.handlerException.error.ReturnValue<java.lang.String>
     * @date 2021/7/15 16:45
     */
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ReturnValue<String> handleShiroException(Exception ex) {
        log.error(ex.getMessage());
        return new ReturnValue<>(CommonEnum.ERROR_NO_RIGHT.getError(), CommonEnum.ERROR_NO_RIGHT.getDescription());
    }

    /**
    * @description: 捕捉权限认证失败异常
    * @author wen jun tang
    * @param ex 异常参数
    * @return com.wenjun.handlerException.error.ReturnValue<java.lang.String>
    * @date 2021/7/15 16:45
    */
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public ReturnValue<String> AuthorizationException(Exception ex) {
        log.error(ex.getMessage());
        return new ReturnValue<>(CommonEnum.ERROR_NO_RIGHT_ERROR.getError(), CommonEnum.ERROR_NO_RIGHT_ERROR.getDescription());
    }

    /**
     * @param e 异常参数
     * @return com.study.error.ReturnValue<java.lang.String>
     * @description: 空指针的异常
     * @author tang wen jun
     * @date 2021/5/11 18:30
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ReturnValue<Object> nullPointerException(NullPointerException e) {
        log.error(e.getMessage());
        return new ReturnValue<>(CommonEnum.ERROR_NOT_FOUND.getError(), e.getMessage());
    }

    /**
     * @param e 异常参数
     * @return com.study.error.ReturnValue<java.lang.String>
     * @description: 类型强制转换异常
     * @author tang wen jun
     * @date 2021/5/11 18:29
     */
    @ExceptionHandler(value = ClassCastException.class)
    @ResponseBody
    public ReturnValue<String> classCastException(ClassCastException e) {
        log.error(e.getMessage());
        return new ReturnValue<>(CommonEnum.ERROR_CLASS_CAST_EXCEPTION, e.getMessage());
    }

    /**
     * @param e 异常参数
     * @return com.study.error.ReturnValue<java.lang.String>
     * @description: 数组下标越界异常
     * @author tang wen jun
     * @date 2021/5/11 18:36
     */
    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public ReturnValue<String> negativeArrayException(ArrayIndexOutOfBoundsException e) {
        log.error(e.getMessage());
        return new ReturnValue<>(CommonEnum.ERROR_ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION, e.getMessage());
    }



    /**
     * @param e 异常参数
     * @return com.wenjun.handlerException.error.ReturnValue<java.lang.String>
     * @description: 处理其他异常
     * @author wen jun tang
     * @date 2021/7/14 17:16
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnValue<String> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new ReturnValue<>(e.getMessage());
    }
}
