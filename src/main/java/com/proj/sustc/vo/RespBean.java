package com.proj.sustc.vo;

public class RespBean {
    private Integer code;
    private String message;
    private Object object;

    public RespBean(Integer code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static RespBean login_success() {
        return new RespBean(RespBeanEnum.LOGIN_SUCCESS.getCode(), RespBeanEnum.LOGIN_SUCCESS.getMessage(), null);
    }

    public static RespBean login_success(Object object) {
        return new RespBean(RespBeanEnum.LOGIN_SUCCESS.getCode(), RespBeanEnum.LOGIN_SUCCESS.getMessage(), object);
    }

    public static RespBean login_error() {
        return new RespBean(RespBeanEnum.LOGIN_ERROR.getCode(), RespBeanEnum.LOGIN_ERROR.getMessage(), null);
    }

    public static RespBean login_error(Object object) {
        return new RespBean(RespBeanEnum.LOGIN_ERROR.getCode(), RespBeanEnum.LOGIN_ERROR.getMessage(), object);
    }

    public static RespBean operation_success() {
        return new RespBean(RespBeanEnum.OPERATION_SUCCESS.getCode(), RespBeanEnum.OPERATION_SUCCESS.getMessage(), null);
    }

    public static RespBean operation_success(Object object) {
        return new RespBean(RespBeanEnum.OPERATION_SUCCESS.getCode(), RespBeanEnum.OPERATION_SUCCESS.getMessage(), object);
    }

    public static RespBean operation_error() {
        return new RespBean(RespBeanEnum.OPERATION_ERROR.getCode(), RespBeanEnum.OPERATION_ERROR.getMessage(), null);
    }


    public static RespBean operation_error(Object object) {
        return new RespBean(RespBeanEnum.OPERATION_ERROR.getCode(), RespBeanEnum.OPERATION_ERROR.getMessage(), object);
    }

    public static RespBean operation_error1() {
        return new RespBean(RespBeanEnum.OPERATION_ERROR1.getCode(), RespBeanEnum.OPERATION_ERROR1.getMessage(), null);
    }

    public static RespBean operation_error1(Object object) {
        return new RespBean(RespBeanEnum.OPERATION_ERROR1.getCode(), RespBeanEnum.OPERATION_ERROR1.getMessage(), object);

    }

    public static RespBean not_match() {
        return new RespBean(RespBeanEnum.NOT_MATTCH.getCode(), RespBeanEnum.NOT_MATTCH.getMessage(), null);
    }

    public static RespBean not_match(Object object) {
        return new RespBean(RespBeanEnum.NOT_MATTCH.getCode(), RespBeanEnum.NOT_MATTCH.getMessage(), object);
    }

    public static RespBean not_exist() {
        return new RespBean(RespBeanEnum.NOT_EXIST.getCode(), RespBeanEnum.NOT_EXIST.getMessage(), null);
    }

    public static RespBean not_exist(Object object) {
        return new RespBean(RespBeanEnum.NOT_EXIST.getCode(), RespBeanEnum.NOT_EXIST.getMessage(), object);
    }


    public static RespBean generate_error() {
        return new RespBean(RespBeanEnum.Generate_ERROR.getCode(), RespBeanEnum.Generate_ERROR.getMessage(), null);
    }

    public static RespBean generate_error(Object object) {
        return new RespBean(RespBeanEnum.Generate_ERROR.getCode(), RespBeanEnum.Generate_ERROR.getMessage(), object);
    }

    public static RespBean stock_error() {
        return new RespBean(RespBeanEnum.Stock_ERROR.getCode(), RespBeanEnum.Stock_ERROR.getMessage(), null);
    }

    public static RespBean stock_error(Object object) {
        return new RespBean(RespBeanEnum.Stock_ERROR.getCode(), RespBeanEnum.Stock_ERROR.getMessage(), object);
    }

    public static RespBean phone_error() {
        return new RespBean(RespBeanEnum.Phone_ERROR.getCode(), RespBeanEnum.Phone_ERROR.getMessage(), null);
    }

    public static RespBean phone_error(Object object) {
        return new RespBean(RespBeanEnum.Phone_ERROR.getCode(), RespBeanEnum.Phone_ERROR.getMessage(), object);
    }




}
