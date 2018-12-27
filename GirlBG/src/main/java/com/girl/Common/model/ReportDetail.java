package com.girl.Common.model;

import java.util.Date;

/**
 * 举报详情
 */
public class ReportDetail {

    /**
     * 举报时间
     */
    private Date createTime;

    /**
     * 举报人
     */
    private String reportName;

    /**
     * 举报原因
     */
    private Integer type;

    /**
     * 举报次数
     */
    private Integer reportTimes;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReportTimes() {
        return reportTimes;
    }

    public void setReportTimes(Integer reportTimes) {
        this.reportTimes = reportTimes;
    }
}
