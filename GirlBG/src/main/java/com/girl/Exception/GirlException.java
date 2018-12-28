package com.girl.Exception;

public class GirlException extends Throwable {

    private int code;

    private String message;

    public GirlException(String message) {
        this.message = message;
    }

    public GirlException(int code, String message){
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
