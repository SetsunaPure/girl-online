package com.girl.Common.model;

/**
 * 动态(审核)
 */
public class DynamicContext {

    /**文字**/
    private String word;

    /**照片**/
    private String photos;

    /**视频**/
    private String video;

    public DynamicContext(String word, String photos, String video) {
        this.word = word;
        this.photos = photos;
        this.video = video;
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
