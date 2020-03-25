package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyFavorite
{
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fav_id")
    @Expose
    private String favId;
    @SerializedName("car_id")
    @Expose
    private String carId;
    @SerializedName("car_name")
    @Expose
    private String carName;
    @SerializedName("car_description")
    @Expose
    private String carDescription;
    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("car_category")
    @Expose
    private String carCategory;
    @SerializedName("cars")
    @Expose
    private List<Car_image> cars = null;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sell_price")
    @Expose
    private String sell_price;
    @SerializedName("phone_no")
    @Expose
    private String phone_no;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public List<Car_image> getCars() {
        return cars;
    }

    public void setCars(List<Car_image> cars) {
        this.cars = cars;
    }

}
