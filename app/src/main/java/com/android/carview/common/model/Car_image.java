package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car_image{
        @SerializedName("car_id")
        @Expose
        private String carId;
        @SerializedName("car_image")
        @Expose
        private String carImage;

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getCarImage() {
            return carImage;
        }

        public void setCarImage(String carImage) {
            this.carImage = carImage;
        }

}