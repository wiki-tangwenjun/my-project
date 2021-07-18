package com.wenjun.enume;


public enum EOperateType {
	ADD(1, "添加"),
	ANALYZE(5, "分析"),
	DELETE(2, "删除"),
	EXPORT(8, "导出"),
	LOGIN(6, "登录"),
	LOGOUT(7, "登出"),
	QUERY(4, "查询"),
	UNKNOWN(0, "未知"),
	UPDATE(3, "修改");

	private int value = 0;
	private String description = "未知";

	private EOperateType(int value, String description) {

		this.value = value;
		this.description = description;
	}

	public byte getValue() {
		return (byte) value;
	}

	public String getDescription() {
		return description;
	}
}
