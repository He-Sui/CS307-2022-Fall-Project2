package com.proj.sustc.vo;

public enum RespBeanEnum {
    LOGIN_SUCCESS(100, "成功登录"),
    LOGIN_ERROR(200, "登陆失败，账号或者用户名不对"),
    EXIST_ERROR(300, "用户名不存在"),
    OPERATION_ERROR(400, "您没有操作权限"),
    OPERATION_SUCCESS(500, "操作成功"),
    OPERATION_ERROR1(600, "操作失败"),
    NOT_MATTCH(700, "两次密码输入不一致"),
    NOT_EXIST(800, "输入的工号找不到相应的员工，请检查输入是否正确以及该员工是否还在职"),
    Generate_ERROR(900,"生成订单失败"),
    Stock_ERROR(1000,"入库失败"),
    Phone_ERROR(1100,"输入手机格式不对")
    ;

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
