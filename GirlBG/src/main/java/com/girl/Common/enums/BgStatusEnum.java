package com.girl.Common.enums;

public enum BgStatusEnum {

    RESPONSE_OK(200, "返回正常"),
    RESPONSE_ERROR(400, "返回异常");

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
