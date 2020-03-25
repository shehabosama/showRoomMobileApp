package com.android.carview.common.model;

import com.google.gson.annotations.SerializedName;

public class SparCar
{
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("spare_name")
    private String spare_name;
    @SerializedName("spare_description")
    private String spar_description;
    @SerializedName("spare_image")
    private String spar_image;
    @SerializedName("location")
    private String location;
    @SerializedName("price")
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSpare_name() {
        return spare_name;
    }

    public void setSpare_name(String spare_name) {
        this.spare_name = spare_name;
    }

    public String getSpar_description() {
        return spar_description;
    }

    public void setSpar_description(String spar_description) {
        this.spar_description = spar_description;
    }

    public String getSpar_image() {
        return spar_image;
    }

    public void setSpar_image(String spar_image) {
        this.spar_image = spar_image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
