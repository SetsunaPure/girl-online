package com.girl.Common.model;

import java.util.Date;

public class MeetContent {

    private Date meettime;

    private String meetplace;

    private String purpose;

    private String images;

    public Date getMeettime() {
        return meettime;
    }

    public void setMeettime(Date meettime) {
        this.meettime = meettime;
    }

    public String getMeetplace() {
        return meetplace;
    }

    public void setMeetplace(String meetplace) {
        this.meetplace = meetplace;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
