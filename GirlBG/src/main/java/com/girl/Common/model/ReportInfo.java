package com.girl.Common.model;

import java.util.Date;

public class ReportInfo{

    /**
     * 审核状态（0未审核、1通过、2未通过）
     */
    private Integer status;

    /**
     * 被举报人
     */
    private Integer id;
    /**
     * 被举报人姓名
     */
    private String name;
    /**
     * 举报类型1暴力，2广告、3虚假
     */
    private Integer type;
    /**
     * 举报时间
     */
    private Date createTime;
    /**
     * 举报次数
     */
    private Integer reportTimes;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getReportTimes() {
        return reportTimes;
    }

    public void setReportTimes(Integer reportTimes) {
        this.reportTimes = reportTimes;
    }
}
