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
        return new ReturnValue(e.getError(), e.getDescription());
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
    public ReturnValue<String> NullPointerException(HttpServletRequest req, NullPointerException e){
        return  new ReturnValue(CommonEnum.ERROR_NOT_FOUND);
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
        return  new ReturnValue(CommonEnum.ERROR_CLASS_CAST_EXCEPTION);
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
        return  new ReturnValue(CommonEnum.ERROR_ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION);
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
