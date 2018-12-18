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
 * @since 2018-12-17
 */
@TableName("user_identity")
public class UserIdentity extends Model<UserIdentity> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long uid;
    /**
     * 激光密码
     */
    private String jgpass;
    /**
     * 网易云账户
     */
    @TableField("wy_accid")
    private String wyAccid;
    /**
     * 女神币
     */
    private Integer coin;
    /**
     * 支付宝账户
     */
    private String zhifubao;
    /**
     * vip有效日期
     */
    @TableField("vip_expiry_date")
    private Date vipExpiryDate;
    /**
     * 是否vip
     */
    private Integer vip;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getJgpass() {
        return jgpass;
    }

    public void setJgpass(String jgpass) {
        this.jgpass = jgpass;
    }

    public String getWyAccid() {
        return wyAccid;
    }

    public void setWyAccid(String wyAccid) {
        this.wyAccid = wyAccid;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public String getZhifubao() {
        return zhifubao;
    }

    public void setZhifubao(String zhifubao) {
        this.zhifubao = zhifubao;
    }

    public Date getVipExpiryDate() {
        return vipExpiryDate;
    }

    public void setVipExpiryDate(Date vipExpiryDate) {
        this.vipExpiryDate = vipExpiryDate;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

    @Override
    public String toString() {
        return "UserIdentity{" +
        ", uid=" + uid +
        ", jgpass=" + jgpass +
        ", wyAccid=" + wyAccid +
        ", coin=" + coin +
        ", zhifubao=" + zhifubao +
        ", vipExpiryDate=" + vipExpiryDate +
        ", vip=" + vip +
        "}";
    }
}
