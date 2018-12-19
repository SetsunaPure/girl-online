package com.girl.Common.model;

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
