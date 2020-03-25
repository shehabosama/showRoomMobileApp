package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EmergenceResponse
{
    @SerializedName("emergences")
    @Expose
    private List<Emergence> emergences = new ArrayList<>();

    public List<Emergence> getEmergences() {
        return emergences;
    }

    public void setEmergences(List<Emergence> emergences) {
        this.emergences = emergences;
    }

}