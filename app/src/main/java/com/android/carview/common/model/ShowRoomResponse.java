package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowRoomResponse
{
    @SerializedName("show_room")
    @Expose
    private List<ShowRoom> showRoom = null;

    public List<ShowRoom> getShowRoom() {
        return showRoom;
    }

    public void setShowRoom(List<ShowRoom> showRoom) {
        this.showRoom = showRoom;
    }

}
