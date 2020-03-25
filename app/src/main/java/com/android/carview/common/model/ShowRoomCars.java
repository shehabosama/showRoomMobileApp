package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShowRoomCars
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sh_id")
    @Expose
    private String shId;
    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("car_name")
    @Expose
    private String carName;
    @SerializedName("car_description")
    @Expose
    private String carDescription;
    @SerializedName("cars")
    @Expose
    private List<ShowRoomCarimage> cars = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShId() {
        return shId;
    }

    public void setShId(String shId) {
        this.shId = shId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
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

    public List<ShowRoomCarimage> getCars() {
        return cars;
    }

    public void setCars(List<ShowRoomCarimage> cars) {
        this.cars = cars;
    }
}
