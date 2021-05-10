package com.study.error;

public enum ErrorCode { // 数字值保持与c++后台一致
	ERROR_SUCCESS(0, "成功"),						
	ERROR_SERVER_ERROR(-500, "服务内部错误"),		
	ERROR_BUSY(-429, "系统繁忙，请稍后重试"),
	ERROR_NOT_FOUND(-1, "未找到或不存在"),								
	ERROR_HANDLE_TIMEOUT(-2, "超时"),	
	ERROR_INVALID_PARAM(-3, "无效参数"),			
	ERROR_CHECK_CODE(-4, "验证码错误或已失效"),
	ERROR_MEMORY_OUT(-5, "内存溢出"),		
	ERROR_OBJECT_EXIST(-6, "对象已存在"),
	ERROR_USER_PASSWORD(-7, "用户名或密码不正确"),
	ERROR_DELETE_FAIL(-8, "删除对象失败"),
	ERROR_INVALID_ARRAY(-9, "无效列表"),
	ERROR_EXIST_TIMESPAN(-10, "该时间段包含已有时间"),
	ERROR_NOT_LOGIN(-10, "未登录或会话过期"),
	ERROR_NOT_SUPPORT(-11, "接口不支持"),
	ERROR_NO_RIGHT(-12, "权限不足"),
	ERROR_NOT_AVAILABLE(-13, "服务暂不可用"),
	ERROR_USER_HAS_LOGIN(-14, "用户已登录"),
	ERROR_IP_NOT_RIGHT(-15, "访问IP无权限"),
	ERROR_WRITE_FAILED(-41, "写文件失败"),
	ERROR_READ_FAILED(-42, "读文件失败"),
	ERROR_UPLOAD_FAILED(-43, "上传文件失败"),
	ERROR_UNKNOW(-50, "未知错误"),
	ERROR_REGION_LIMIT(-51, "区域已经达到最大个数"),
	ERROR_BATCH_SOME_SUCCESS(-52, "批量操作部分成功"),
	ERROR_BATCH_ALL_FAILED(-53, "批量操作全部失败"),
	ERROR_TIME_TRANSFORMATIONFIAL(-54, "时间转换失败"),
	ERROR_ENTERPRISE(-55, "档案企业信息格式有误"),
	ERROR_ARCHIVES(-56, "个人档案信息格式有误"),
	ERROR_RELATIVES(-57, "档案亲戚关系格式有误"),
	ERROR_HOUSE(-58, "档案房屋信息格式有误"),
	ERROR_PARTTIMEJOB(-59, "档案兼职信息有误"),
	ERROR_COMPARISON(-60, "档案校验信息有误"),
	ERROR_PERSONID(-61, "档案身份证号码异常")
	;		
	
	private Integer error;	
	private String description;	
	
	private ErrorCode(Integer error, String description){
		this.error = error;
		this.description = description;
	}
	
	public Integer getError(){
		return this.error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setError(Integer error) {
		this.error = error;
	}
	
	static public String getDescription(Integer error) {
		String desc = "";
		if(ERROR_SUCCESS.getError() == error){
			desc = ERROR_SUCCESS.getDescription();
		}
		if(ERROR_SERVER_ERROR.getError() == error){
			desc = ERROR_SERVER_ERROR.getDescription();
		}
		if(ERROR_BUSY.getError() == error){
			desc = ERROR_BUSY.getDescription();
		}
		if(ERROR_NOT_FOUND.getError() == error){
			desc = ERROR_NOT_FOUND.getDescription();
		}
		if(ERROR_HANDLE_TIMEOUT.getError() == error){
			desc = ERROR_HANDLE_TIMEOUT.getDescription();
		}
		if(ERROR_INVALID_PARAM.getError() == error){
			desc = ERROR_INVALID_PARAM.getDescription();
		}								
		if(ERROR_CHECK_CODE.getError() == error){
			desc = ERROR_CHECK_CODE.getDescription();
		}								
		if(ERROR_MEMORY_OUT.getError() == error){
			desc = ERROR_MEMORY_OUT.getDescription();
		}								
		if(ERROR_OBJECT_EXIST.getError() == error){
			desc = ERROR_OBJECT_EXIST.getDescription();
		}
		if(ERROR_USER_PASSWORD.getError() == error){
			desc = ERROR_USER_PASSWORD.getDescription();
		}
		if(ERROR_DELETE_FAIL.getError() == error){
			desc = ERROR_DELETE_FAIL.getDescription();
		}
		if(ERROR_INVALID_ARRAY.getError() == error){
			desc = ERROR_INVALID_ARRAY.getDescription();
		}
		if(ERROR_EXIST_TIMESPAN.getError() == error){
			desc = ERROR_EXIST_TIMESPAN.getDescription();
		}
		if(ERROR_NOT_LOGIN.getError() == error){
			desc = ERROR_NOT_LOGIN.getDescription();
		}
		if(ERROR_NOT_SUPPORT.getError() == error){
			desc = ERROR_NOT_SUPPORT.getDescription();
		}
		if(ERROR_NO_RIGHT.getError() == error){
			desc = ERROR_NO_RIGHT.getDescription();
		}
		if(ERROR_NOT_AVAILABLE.getError() == error){
			desc = ERROR_NOT_AVAILABLE.getDescription();
		}
		return desc;
	}
}