package com.girl.Common.model;

import java.util.Date;

public class PubApiToken {

    private Date createTime;

    private String token;

    private String loginId;

    private Integer authsId;

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

    public Integer getAuthsId() {
        return authsId;
    }

    public void setAuthsId(Integer authsId) {
        this.authsId = authsId;
    }
}
