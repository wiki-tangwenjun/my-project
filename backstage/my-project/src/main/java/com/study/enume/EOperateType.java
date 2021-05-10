package com.study.enume;

/**
 * @author tangwenjun
 */
public enum EOperateType {
	  UNKNOWN(0,"未知"),
	  ADD(1,"添加"),
	  DELETE(2,"删除"),
	  UPDATE(3,"修改"),
	  QUERY(4,"查询"),
	  ANALYZE(5,"分析"),
	  LOGIN(6,"登录"),
	  LOGOUT(7,"登出"),
	  EXPORT(8,"导出");
 
	private int value = 0;
	private String description = "未知";

	private EOperateType(int value,String description){

		this.value = value;
		this.description = description;
	}
	
	public byte getValue(){
		return (byte)value;
	}
	
	public String getDescription(){
		return description;
	}
}
