package com.proj.sustc.vo;

public enum RespBeanEnum {
    LOGIN_SUCCESS(100, "成功登录"),
    LOGIN_ERROR(200, "登陆失败，账号或者用户名不对"),
    EXIST_ERROR(300, "用户名不存在"),
    OPERATION_ERROR(400, "您没有操作权限"),
    OPERATION_SUCCESS(500, "操作成功");

    private RespBeanEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

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
}
