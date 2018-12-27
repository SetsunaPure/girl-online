package com.girl.Common.model;

public class ResponseData {

    private Long count;

    private Object info;

    public ResponseData(Long count, Object info) {
        this.count = count;
        this.info = info;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
