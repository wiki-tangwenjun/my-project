package com.wenjun.handlerException.error;

import com.wenjun.handlerException.error.service.BaseErrorInfoInterface;

/**
 * @projectName: my-project
 * @package: com.study.error
 * @className: CommonEnum
 * @author: tang wen jun
 * @description: &#x5F02;&#x5E38;&#x679A;&#x4E3E;&#x7C7B;
 * @date: 2021&#x5E74;05&#x6708;11&#x65E5; 17:10
 * @version: 1.0
 */
public enum CommonEnum implements BaseErrorInfoInterface {
    ERROR_SUCCESS("0", "成功"),
    ERROR_SERVER_ERROR("-500", "服务内部错误"),
    ERROR_BUSY("-429", "系统繁忙，请稍后重试"),
    ERROR_NOT_FOUND("-1", "未找到或不存在"),
    ERROR_HANDLE_TIMEOUT("-2", "超时"),
    ERROR_INVALID_PARAM("-3", "无效参数"),
    ERROR_CHECK_CODE("-4", "验证码错误或已失效"),
    ERROR_MEMORY_OUT("-5", "内存溢出"),
    ERROR_OBJECT_EXIST("-6", "对象已存在"),
    ERROR_USER_PASSWORD("-7", "用户名或密码不正确"),
    ERROR_DELETE_FAIL("-8", "删除对象失败"),
    ERROR_INVALID_ARRAY("-9", "无效列表"),
    ERROR_NOT_LOGIN("-10", "未登录或会话过期"),
    ERROR_NOT_SUPPORT("-11", "接口不支持"),
    ERROR_NO_RIGHT("-12", "权限不足"),
    ERROR_NOT_AVAILABLE("-13", "服务暂不可用"),
    ERROR_USER_HAS_LOGIN("-14", "用户已登录"),
    ERROR_IP_NOT_RIGHT("-15", "访问IP无权限"),
    ERROR_WRITE_FAILED("-16", "写文件失败"),
    ERROR_READ_FAILED("-17", "读文件失败"),
    ERROR_UPLOAD_FAILED("-18", "上传文件失败"),

    ERROR_CLASS_CAST_EXCEPTION("-40", "类型强制转换异常"),
    ERROR_NEGATIVE_ARRAY_EXCEPTION("-41", "数组负下标异常"),
    ERROR_ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION("42", "数组下标越界异常"),
    ERROR_USER_NOT_FOUND("43", "用户不存在"),

    ERROR_UNKNOW("-100", "未知错误"),
    ;

    private String error;
    private String description;

    private CommonEnum(String error, String description){
        this.error = error;
        this.description = description;
    }

    public String getError(){
        return this.error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setError(String error) {
        this.error = error;
    }

    static public String getDescription(String error) {
        String desc = "";
        if(ERROR_SUCCESS.getError().equals(error)){
            desc = ERROR_SUCCESS.getDescription();
        }
        if(ERROR_SERVER_ERROR.getError().equals(error)){
            desc = ERROR_SERVER_ERROR.getDescription();
        }
        if(ERROR_BUSY.getError().equals(error)){
            desc = ERROR_BUSY.getDescription();
        }
        if(ERROR_NOT_FOUND.getError().equals(error)){
            desc = ERROR_NOT_FOUND.getDescription();
        }
        if(ERROR_HANDLE_TIMEOUT.getError().equals(error)){
            desc = ERROR_HANDLE_TIMEOUT.getDescription();
        }
        if(ERROR_INVALID_PARAM.getError().equals(error)){
            desc = ERROR_INVALID_PARAM.getDescription();
        }
        if(ERROR_CHECK_CODE.getError().equals(error)){
            desc = ERROR_CHECK_CODE.getDescription();
        }
        if(ERROR_MEMORY_OUT.getError().equals(error)){
            desc = ERROR_MEMORY_OUT.getDescription();
        }
        if(ERROR_OBJECT_EXIST.getError().equals(error)){
            desc = ERROR_OBJECT_EXIST.getDescription();
        }
        if(ERROR_USER_PASSWORD.getError().equals(error)){
            desc = ERROR_USER_PASSWORD.getDescription();
        }
        if(ERROR_DELETE_FAIL.getError().equals(error)){
            desc = ERROR_DELETE_FAIL.getDescription();
        }
        if(ERROR_INVALID_ARRAY.getError().equals(error)){
            desc = ERROR_INVALID_ARRAY.getDescription();
        }
        if(ERROR_NOT_LOGIN.getError().equals(error)){
            desc = ERROR_NOT_LOGIN.getDescription();
        }
        if(ERROR_NOT_SUPPORT.getError().equals(error)){
            desc = ERROR_NOT_SUPPORT.getDescription();
        }
        if(ERROR_NO_RIGHT.getError().equals(error)){
            desc = ERROR_NO_RIGHT.getDescription();
        }
        if(ERROR_NOT_AVAILABLE.getError().equals(error)){
            desc = ERROR_NOT_AVAILABLE.getDescription();
        }
        if(ERROR_CLASS_CAST_EXCEPTION.getError().equals(error)){
            desc = ERROR_CLASS_CAST_EXCEPTION.getDescription();
        }
        if(ERROR_NEGATIVE_ARRAY_EXCEPTION.getError().equals(error)){
            desc = ERROR_NEGATIVE_ARRAY_EXCEPTION.getDescription();
        }
        if(ERROR_ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION.getError().equals(error)){
            desc = ERROR_ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION.getDescription();
        }

        return desc;
    }

    @Override
    public String getResultCode() {
        return error;
    }

    @Override
    public String getResultMsg() {
        return description;
    }
}
