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
 * @since 2018-12-28
 */
@TableName("user_info")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 地址
     */
    private String address;
    /**
     * 感情状态
     */
    @TableField("emotion_status")
    private Integer emotionStatus;
    /**
     * 身高
     */
    private Integer height;
    /**
     * 体重
     */
    @TableField("body_weight")
    private Integer bodyWeight;
    /**
     * 最满意部位
     */
    @TableField("best_part")
    private Integer bestPart;
    /**
     * 微信号
     */
    @TableField("wx_code")
    private String wxCode;
    /**
     * QQ号
     */
    @TableField("qq_code")
    private String qqCode;
    /**
     * 标签
     */
    private String label;
    /**
     * 手机号码
     */
    @TableField("phone_number")
    private String phoneNumber;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 是否认证
     */
    private Integer certification;
    /**
     * 是否完善资料
     */
    @TableField("perfect_userinfo")
    private Integer perfectUserinfo;
    /**
     * 职业(0未知)
     */
    private Integer job;
    /**
     * 约会态度
     */
    @TableField("dating_attitude")
    private Integer datingAttitude;
    /**
     * 是否是VIP
     */
    private Integer vip;
    /**
     * 注册时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 最后一次登陆时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
    /**
     * 审核状态(0正常，1屏蔽)
     */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmotionStatus() {
        return emotionStatus;
    }

    public void setEmotionStatus(Integer emotionStatus) {
        this.emotionStatus = emotionStatus;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Integer bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public Integer getBestPart() {
        return bestPart;
    }

    public void setBestPart(Integer bestPart) {
        this.bestPart = bestPart;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getQqCode() {
        return qqCode;
    }

    public void setQqCode(String qqCode) {
        this.qqCode = qqCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCertification() {
        return certification;
    }

    public void setCertification(Integer certification) {
        this.certification = certification;
    }

    public Integer getPerfectUserinfo() {
        return perfectUserinfo;
    }

    public void setPerfectUserinfo(Integer perfectUserinfo) {
        this.perfectUserinfo = perfectUserinfo;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public Integer getDatingAttitude() {
        return datingAttitude;
    }

    public void setDatingAttitude(Integer datingAttitude) {
        this.datingAttitude = datingAttitude;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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
        return "UserInfo{" +
        ", id=" + id +
        ", nickName=" + nickName +
        ", sex=" + sex +
        ", birthday=" + birthday +
        ", address=" + address +
        ", emotionStatus=" + emotionStatus +
        ", height=" + height +
        ", bodyWeight=" + bodyWeight +
        ", bestPart=" + bestPart +
        ", wxCode=" + wxCode +
        ", qqCode=" + qqCode +
        ", label=" + label +
        ", phoneNumber=" + phoneNumber +
        ", avatar=" + avatar +
        ", certification=" + certification +
        ", perfectUserinfo=" + perfectUserinfo +
        ", job=" + job +
        ", datingAttitude=" + datingAttitude +
        ", vip=" + vip +
        ", createTime=" + createTime +
        ", lastLoginTime=" + lastLoginTime +
        ", status=" + status +
        "}";
    }
}
