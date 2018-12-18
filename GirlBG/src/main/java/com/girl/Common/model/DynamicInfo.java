package com.girl.Common.model;

import java.util.Date;

public class DynamicInfo {

    /**推送时间**/
    private Date publishtime;

    /**用户id**/
    private String userid;

    /**昵称**/
    private String nickname;

//    /**动态内容**/
//    private DynamicContext context;

    /**文字**/
    private String word;

    /**照片**/
    private String photos;

    /**视频**/
    private String video;

    public DynamicInfo() {
    }

    public DynamicInfo(Date publishtime, String userid, String nickname, String word, String photos, String video) {
        this.publishtime = publishtime;
        this.userid = userid;
        this.nickname = nickname;
        this.word = word;
        this.photos = photos;
        this.video = video;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
