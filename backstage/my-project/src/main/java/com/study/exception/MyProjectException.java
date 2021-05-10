package com.study.exception;

import com.study.error.ErrorCode;

/**
 * @author tanwenjun
 * @date 2021/5/10
 */
public class MyProjectException extends Exception{

	private static final long serialVersionUID = 1L;
	private	ErrorCode errorCode;
	
	public void setErrorCode (ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode () {
		return errorCode;
	}
	
	public MyProjectException(ErrorCode code, String  msg){
		super(msg);
		errorCode = code;
	}
}
