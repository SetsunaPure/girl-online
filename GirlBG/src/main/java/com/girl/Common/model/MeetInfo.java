package com.girl.Common.model;

import java.util.Date;

/**
 * 约会信息
 */
public class MeetInfo {

    private int id;

    private Date publishtime;

    private Integer userid;

    private String nickname;

    //    private MeetContent meetContent;
    private Date meettime;

    private String meetplace;

    private String purpose;

    private String images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userId) {
        this.userid = userId;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickName) {
        this.nickname = nickName;
    }

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
