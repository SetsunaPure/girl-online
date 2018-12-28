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
     * id
     */
    private String reportId;

    /**
     * 举报人
     */
    private String reportName;

    /**
     * 举报原因
     */
    private Integer reportType;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }
}
