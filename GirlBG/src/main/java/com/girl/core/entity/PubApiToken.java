package com.girl.core.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangpei
 * @since 2018-12-28
 */
@TableName("pub_api_token")
public class PubApiToken extends Model<PubApiToken> {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    private String token;
    /**
     * 用户id
     */
    @TableField("login_id")
    private String loginId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 凭证id
     */
    @TableField("auths_id")
    private String authsId;
    /**
     * 有效期
     */
    @TableField("expiry_time")
    private Date expiryTime;


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAuthsId() {
        return authsId;
    }

    public void setAuthsId(String authsId) {
        this.authsId = authsId;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.token;
    }

    @Override
    public String toString() {
        return "PubApiToken{" +
        ", token=" + token +
        ", loginId=" + loginId +
        ", createTime=" + createTime +
        ", authsId=" + authsId +
        ", expiryTime=" + expiryTime +
        "}";
    }
}
