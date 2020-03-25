package com.android.carview.common.model;

import java.util.ArrayList;
import java.util.List;

public class UserListResponse
{
    private List<UserList> userLists = new ArrayList<>();

    public List<UserList> getUserLists() {
        return userLists;
    }

    public void setUserLists(List<UserList> userLists) {
        this.userLists = userLists;
    }
}
