package com.girl.core.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-12-17
 */
@TableName("user_cert_info")
public class UserCertInfo extends Model<UserCertInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 认证状态
     */
    private Long uid;
    /**
     * 视屏地址
     */
    @TableField("video_url")
    private String videoUrl;
    /**
     * 0认证中，1成功，2失败
     */
    private Integer status;
    /**
     * 处理意见
     */
    @TableField("oper_msg")
    private String operMsg;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperMsg() {
        return operMsg;
    }

    public void setOperMsg(String operMsg) {
        this.operMsg = operMsg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserCertInfo{" +
        ", id=" + id +
        ", uid=" + uid +
        ", videoUrl=" + videoUrl +
        ", status=" + status +
        ", operMsg=" + operMsg +
        "}";
    }
}
