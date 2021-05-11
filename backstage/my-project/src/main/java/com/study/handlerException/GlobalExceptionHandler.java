package com.study.handlerException;

import com.study.error.CommonEnum;
import com.study.error.ReturnValue;
import com.study.exception.MyProjectException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MyProjectException.class)
    @ResponseBody
    public ReturnValue<String> bizExceptionHandler(HttpServletRequest req, CommonEnum e){
        return new ReturnValue(e.getError(), e.getDescription());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ReturnValue<String> exceptionHandler(HttpServletRequest req, NullPointerException e){
        return  new ReturnValue(CommonEnum.ERROR_NOT_FOUND);
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ReturnValue<String> exceptionHandler(HttpServletRequest req, Exception e){
        return new ReturnValue(CommonEnum.ERROR_UNKNOW);
    }
}
