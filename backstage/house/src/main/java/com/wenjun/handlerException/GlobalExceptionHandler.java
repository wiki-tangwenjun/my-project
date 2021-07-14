package com.wenjun.handlerException;

import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import com.wenjun.handlerException.exception.HouseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
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
     * @param e 异常参数
     * @return com.wenjun.handlerException.error.ReturnValue<java.lang.String>
     * @description: 捕捉shiro的异常
     * @author wen jun tang
     * @date 2021/7/14 17:14
     */
    @ExceptionHandler(ShiroException.class)
    public ReturnValue<String> shiroException(CommonEnum e) {
        return new ReturnValue<>(CommonEnum.ERROR_NO_RIGHT.getError(), CommonEnum.ERROR_NO_RIGHT.getDescription());
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
        e.printStackTrace();
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
        e.printStackTrace();
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
        e.printStackTrace();
        log.error(e.getMessage());
        return new ReturnValue<>(CommonEnum.ERROR_UNKNOW, e.getMessage());
    }
}
