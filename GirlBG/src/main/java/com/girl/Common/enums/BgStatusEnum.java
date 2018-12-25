package com.girl.Common.enums;

public enum BgStatusEnum {

    RESPONSE_OK(200, "返回正常"),
    RESPONSE_ERROR(400, "返回异常"),
    RESPONSE_EMPTY(401, "参数为空"),
    RESPONSE_NOT_LOGIN(402, "用户未登录"),
    RESPONSE_USER_EXIST(403, "用户名已存在");

    private int code;
    private String message;

    private BgStatusEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
