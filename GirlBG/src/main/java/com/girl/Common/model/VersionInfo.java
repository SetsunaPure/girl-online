package com.girl.Common.model;

/**
 * 发布版本内容
 */
public class VersionInfo {

    /**下载地址**/
    private String android_url;

    /**版本信息**/
    private String info;

    /**版本名称**/
    private String version_name;

    /**版本号**/
    private int version_code;

    /**是否强制更新1强制0不强制**/
    private int update_type;

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public int getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(int update_type) {
        this.update_type = update_type;
    }
}
