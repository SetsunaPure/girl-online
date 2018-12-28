package com.girl.Common.model;

import java.util.Date;

public class OrignReport {

    /**流水id**/
    private int id;

    /**被举报人id**/
    private int uid;

    /**昵称**/
    private String nickName;

    /**举报人id**/
    private int reportId;

    /**举报人昵称**/
    private String reportNickName;

    /**举报原因**/
    private int reportType;

    /**举报时间**/
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportNickName() {
        return reportNickName;
    }

    public void setReportNickName(String reportNickName) {
        this.reportNickName = reportNickName;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
