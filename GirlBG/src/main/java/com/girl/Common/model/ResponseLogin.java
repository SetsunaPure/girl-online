package com.girl.Common.model;

import com.girl.Common.enums.BgStatusEnum;

public class ResponseLogin {

    private int code;

    private String msg;

    private String token;

    private String data;

    public ResponseLogin() {
    }

    public ResponseLogin(int code, String msg, String token, String data) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.data = data;
    }

    public ResponseLogin(BgStatusEnum bgStatusEnum) {
        this.code = bgStatusEnum.getCode();
        this.msg = bgStatusEnum.getMessage();
    }

    public ResponseLogin(BgStatusEnum bgStatusEnum, String token) {
        this.code = bgStatusEnum.getCode();
        this.msg = bgStatusEnum.getMessage();
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
