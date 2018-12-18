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
 * 用户钱包表
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@TableName("user_meet")
public class UserMeet extends Model<UserMeet> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long uid;
    private String title;
    private String desc;
    /**
     * 约会时间
     */
    @TableField("meet_time")
    private Date meetTime;
    /**
     * 约会选择0不限，1上午，2中午，3下午
     */
    @TableField("meet_time_part")
    private Integer meetTimePart;
    @TableField("meet_place")
    private String meetPlace;
    /**
     * 展示图片列表
     */
    @TableField("show_images")
    private String showImages;
    /**
     * 发布时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 发布经度
     */
    @TableField("create_longitude")
    private Double createLongitude;
    /**
     * 发布纬度
     */
    @TableField("create_latitude")
    private Double createLatitude;
    /**
     * -1不限制,0女，1男
     */
    @TableField("accept_sex")
    private Integer acceptSex;
    /**
     * 审核状态（0待审核，1屏蔽，2通过）
     */
    @TableField("bg_status")
    private Integer bgStatus;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(Date meetTime) {
        this.meetTime = meetTime;
    }

    public Integer getMeetTimePart() {
        return meetTimePart;
    }

    public void setMeetTimePart(Integer meetTimePart) {
        this.meetTimePart = meetTimePart;
    }

    public String getMeetPlace() {
        return meetPlace;
    }

    public void setMeetPlace(String meetPlace) {
        this.meetPlace = meetPlace;
    }

    public String getShowImages() {
        return showImages;
    }

    public void setShowImages(String showImages) {
        this.showImages = showImages;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getCreateLongitude() {
        return createLongitude;
    }

    public void setCreateLongitude(Double createLongitude) {
        this.createLongitude = createLongitude;
    }

    public Double getCreateLatitude() {
        return createLatitude;
    }

    public void setCreateLatitude(Double createLatitude) {
        this.createLatitude = createLatitude;
    }

    public Integer getAcceptSex() {
        return acceptSex;
    }

    public void setAcceptSex(Integer acceptSex) {
        this.acceptSex = acceptSex;
    }

    public Integer getBgStatus() {
        return bgStatus;
    }

    public void setBgStatus(Integer bgStatus) {
        this.bgStatus = bgStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserMeet{" +
                ", id=" + id +
                ", uid=" + uid +
                ", title=" + title +
                ", desc=" + desc +
                ", meetTime=" + meetTime +
                ", meetTimePart=" + meetTimePart +
                ", meetPlace=" + meetPlace +
                ", showImages=" + showImages +
                ", createTime=" + createTime +
                ", createLongitude=" + createLongitude +
                ", createLatitude=" + createLatitude +
                ", acceptSex=" + acceptSex +
                ", bgStatus=" + bgStatus +
                "}";
    }
}
