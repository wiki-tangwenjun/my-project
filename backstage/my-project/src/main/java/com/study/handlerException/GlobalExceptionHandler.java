package com.study.handlerException;

import com.study.error.CommonEnum;
import com.study.error.ReturnValue;
import com.study.exception.MyProjectException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {
   /**
    * @description: 处理自定义的业务异常
    * @author tang wen jun
    * @param req
    * @param e
    * @return com.study.error.ReturnValue<java.lang.String>
    * @date 2021/5/11 18:30
    */
    @ExceptionHandler(value = MyProjectException.class)
    @ResponseBody
    public ReturnValue<String> bizExceptionHandler(HttpServletRequest req, CommonEnum e) {
        log.error(e.getError(), e.getDescription());
        return new ReturnValue<String>(e.getError(), e.getDescription());
    }

   /**
    * @description: 空指针的异常
    * @author tang wen jun
    * @param req
    * @param e
    * @return com.study.error.ReturnValue<java.lang.String>
    * @date 2021/5/11 18:30
    */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ReturnValue<Object> NullPointerException(HttpServletRequest req, NullPointerException e){
        log.error(e.getMessage());
        return  new ReturnValue<>(CommonEnum.ERROR_NOT_FOUND.getError(), e.getMessage());
    }

    /**
     * @description: 类型强制转换异常
     * @author tang wen jun
     * @param req
     * @param e
     * @return com.study.error.ReturnValue<java.lang.String>
     * @date 2021/5/11 18:29
     */
    @ExceptionHandler(value =ClassCastException.class)
    @ResponseBody
    public ReturnValue<String> ClassCastException(HttpServletRequest req, ClassCastException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return  new ReturnValue<String>(CommonEnum.ERROR_CLASS_CAST_EXCEPTION, e.getMessage());
    }

    /**
     * @description: 数组下标越界异常
     * @author tang wen jun
     * @param req
     * @param e
     * @return com.study.error.ReturnValue<java.lang.String>
     * @date 2021/5/11 18:36
     */
    @ExceptionHandler(value =ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public ReturnValue<String> NegativeArrayException(HttpServletRequest req, ArrayIndexOutOfBoundsException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return  new ReturnValue<String>(CommonEnum.ERROR_ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION, e.getMessage());
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
        e.printStackTrace();
        log.error(e.getMessage());
        return new ReturnValue<String>(CommonEnum.ERROR_UNKNOW, e.getMessage());
    }
}
