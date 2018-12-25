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
 * 用户动态表
 * </p>
 *
 * @author wangpei
 * @since 2018-12-13
 */
@TableName("user_dynamic")
public class UserDynamic extends Model<UserDynamic> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long uid;
    private String content;
    /**
     * 图片列表最多9张,逗号分隔
     */
    private String images;
    /**
     * 视频地址
     */
    private String video;
    /**
     * 发布时间
     */
    @TableField("create_time")
    private Date createTime;
    @TableField("location_name")
    private String locationName;
    /**
     * 定位经度
     */
    @TableField("location_longitude")
    private Double locationLongitude;
    /**
     * 定位纬度
     */
    @TableField("location_latitude")
    private Double locationLatitude;
    /**
     * 1普通、2图片、3视频
     */
    private Integer type;
    /**
     * 操作状态(1待审核、2屏蔽、3通过)
     */
    @TableField("status")
    private Integer status;


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserDynamic{" +
                ", id=" + id +
                ", uid=" + uid +
                ", content=" + content +
                ", images=" + images +
                ", video=" + video +
                ", createTime=" + createTime +
                ", locationName=" + locationName +
                ", locationLongitude=" + locationLongitude +
                ", locationLatitude=" + locationLatitude +
                ", type=" + type +
                ", status=" + status +
                "}";
    }
}
