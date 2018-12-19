package com.girl.Common.model;

import java.util.Date;

public class BgApiToken {

    private Date createTime;

    private String token;

    private String loginId;

    private Integer operteId;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Integer getOperteId() {
        return operteId;
    }

    public void setOperteId(Integer operteId) {
        this.operteId = operteId;
    }

    @Override
    public String toString() {
        return "{" +
                "createTime:'" + createTime + '\'' +
                ", token:'" + token + '\'' +
                ", loginId:'" + loginId + '\'' +
                ", operteId:'" + operteId + '\'' +
                "}";
    }
}
