package com.wenjun.handlerException.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonInclude(Include.NON_NULL)
public class ReturnValue<T> {
    /**
     * 错误
     */
    private String error = "";
    /**
     *  错误描述
     */
    private String description = "";
    /**
     *  返回值【当error为ERROR_NO_SUCCESS才有可能返回值-判断值是否为空】
      */
    private T value;

    /**
     *     成功不带返回值
      */
    public ReturnValue(){
        this.error = CommonEnum.ERROR_SUCCESS.getError();
        this.description = "成功";
    }

    /**
     * 成功带返回值
     * @param value
     */
    public ReturnValue(T value){
        if(null == value){
            this.error = CommonEnum.ERROR_NOT_FOUND.getError();
            this.description = "没有找到你需要的资源";
        } else {
            this.error = CommonEnum.ERROR_SUCCESS.getError();
            this.description = "成功";
            this.value = value;
        }
    }

    /**
     * 返回错误
     * @param error
     */
    public ReturnValue(CommonEnum error){
        this.error = error.getError();
        this.description = error.getDescription();
    }

    /**
     * 返回错误--对错误描述进行更改
     * @param error
     * @param description
     */
    public ReturnValue(CommonEnum error, String description){
        this.error = error.getError();
        this.description = description;
    }

    /**
     * 返回错误
     * @param error
     */
    public ReturnValue(String error){
        this.error = error;
        this.description = CommonEnum.getDescription(error);
    }

    public ReturnValue(String error, String description){
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public boolean success(){
        return error.equals(CommonEnum.ERROR_SUCCESS.getError());
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    /**
     * 将字符串转为json对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean fromJsonString(String json){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ReturnValue<T> value = mapper.readValue(json, this.getClass());
            this.setError(value.getError());
            this.setDescription(value.getDescription());
            this.setValue(value.getValue());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将本对象转为json字符串
     * @return
     */
    public String toJsonString(){
        String json = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
