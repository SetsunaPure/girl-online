package com.girl.core.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2019-01-14
 */
@TableName("user_msg")
public class UserMsg extends Model<UserMsg> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 10：约单，20收益，30通知
     */
    private Integer type;
    /**
     * 11约别人推送对方，12别人查看告知，21充值，22提现23查看相册赚取女神币；24查看联系方式赚钱，30通知
     */
    @TableField("sub_type")
    private Integer subType;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 被推送用户编号
     */
    private Long uid;
    /**
     * 参数json格式
     */
    private String param;
    /**
     * 被绑定的数据id，约单就是约单记录id，收益就是流水,通知暂无
     */
    @TableField("bind_id")
    private Long bindId;
    /**
     * 0未读，1已读，-1.已经删除
     */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getBindId() {
        return bindId;
    }

    public void setBindId(Long bindId) {
        this.bindId = bindId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
        ", id=" + id +
        ", type=" + type +
        ", subType=" + subType +
        ", msg=" + msg +
        ", createTime=" + createTime +
        ", uid=" + uid +
        ", param=" + param +
        ", bindId=" + bindId +
        ", status=" + status +
        "}";
    }
}
