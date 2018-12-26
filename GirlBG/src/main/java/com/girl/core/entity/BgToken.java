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
 * @since 2018-12-26
 */
@TableName("bg_token")
public class BgToken extends Model<BgToken> {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    private String token;
    /**
     * 用户id
     */
    @TableField("login_id")
    private Integer loginId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
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

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        return "BgToken{" +
        ", token=" + token +
        ", loginId=" + loginId +
        ", createTime=" + createTime +
        ", expiryTime=" + expiryTime +
        "}";
    }
}
