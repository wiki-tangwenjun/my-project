package com.wenjun.handlerException.exception;

import com.wenjun.handlerException.error.service.BaseErrorInfoInterface;

/**
 * @projectName: house
 * @package: com.wenjun.handlerException.exception
 * @className: HouseException
 * @author: wen jun tang
 * @description: 异常返回值类
 * @date: 2021年07月14日 11:54
 * @version: 1.0
 */
public class HouseException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public HouseException() {
        super();
    }

    public HouseException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getResultCode());
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = errorInfoInterface.getResultMsg();
    }

    public HouseException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getResultCode(), cause);
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = errorInfoInterface.getResultMsg();
    }

    public HouseException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public HouseException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public HouseException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

