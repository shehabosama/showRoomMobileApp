package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowRoom
{
    @SerializedName("sh_id")
    @Expose
    private String shId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("shroom_name")
    @Expose
    private String shroomName;
    @SerializedName("shroom_image")
    @Expose
    private String shroomImage;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("category")
    @Expose
    private String category;

    public String getShId() {
        return shId;
    }

    public void setShId(String shId) {
        this.shId = shId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShroomName() {
        return shroomName;
    }

    public void setShroomName(String shroomName) {
        this.shroomName = shroomName;
    }

    public String getShroomImage() {
        return shroomImage;
    }

    public void setShroomImage(String shroomImage) {
        this.shroomImage = shroomImage;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
