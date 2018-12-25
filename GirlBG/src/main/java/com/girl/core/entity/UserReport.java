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
 * @since 2018-12-25
 */
@TableName("user_report")
public class UserReport extends Model<UserReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 审核状态（0未审核、1通过、2未通过）
     */
    private Integer status;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 被举报人
     */
    private Long uid;
    /**
     * 举报人
     */
    @TableField("report_id")
    private Long reportId;
    /**
     * 举报类型1暴力，2广告、3虚假
     */
    private Integer type;
    /**
     * 举报时间
     */
    @TableField("create_time")
    private Date createTime;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserReport{" +
        ", status=" + status +
        ", id=" + id +
        ", uid=" + uid +
        ", reportId=" + reportId +
        ", type=" + type +
        ", createTime=" + createTime +
        "}";
    }
}
