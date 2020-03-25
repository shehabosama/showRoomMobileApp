package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShowRoomCarResponse {
    @SerializedName("show_room_cars")
    @Expose
    private List<ShowRoomCars> showRoomCars = new ArrayList<>();

    public List<ShowRoomCars> getShowRoomCars() {
        return showRoomCars;
    }

    public void setShowRoomCars(List<ShowRoomCars> showRoomCars) {
        this.showRoomCars = showRoomCars;
    }
}
