package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoritesListResponse {

    @SerializedName("MyFavorites")
    @Expose
    private List<MyFavorite> myFavorites = null;

    public List<MyFavorite> getMyFavorites() {
        return myFavorites;
    }

    public void setMyFavorites(List<MyFavorite> myFavorites) {
        this.myFavorites = myFavorites;
    }
}
