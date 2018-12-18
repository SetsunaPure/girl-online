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
 * @since 2018-12-13
 */
@TableName("user_tixian")
public class UserTixian extends Model<UserTixian> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 变更数目
     */
    private Integer coin;
    /**
     * 转换人民币
     */
    private Double money;
    /**
     * 状态，0审核中，1已经提现，2失败
     */
    private Integer status;
    /**
     * 关联用户
     */
    private Long uid;
    /**
     * 变更时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 审核说明
     */
    @TableField("oper_mark")
    private Integer operMark;
    /**
     * 审核人
     */
    @TableField("oper_id")
    private Long operId;
    /**
     * 审核时间
     */
    @TableField("operate_time")
    private Date operateTime;
    /**
     * 支付宝
     */
    private String zhifubao;
    /**
     * 支付宝名称
     */
    @TableField("zhifubao_name")
    private String zhifubaoName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOperMark() {
        return operMark;
    }

    public void setOperMark(Integer operMark) {
        this.operMark = operMark;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getZhifubao() {
        return zhifubao;
    }

    public void setZhifubao(String zhifubao) {
        this.zhifubao = zhifubao;
    }

    public String getZhifubaoName() {
        return zhifubaoName;
    }

    public void setZhifubaoName(String zhifubaoName) {
        this.zhifubaoName = zhifubaoName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserTixian{" +
                ", id=" + id +
                ", coin=" + coin +
                ", money=" + money +
                ", status=" + status +
                ", uid=" + uid +
                ", createTime=" + createTime +
                ", operMark=" + operMark +
                ", operId=" + operId +
                ", operateTime=" + operateTime +
                ", zhifubao=" + zhifubao +
                ", zhifubaoName=" + zhifubaoName +
                "}";
    }
}
