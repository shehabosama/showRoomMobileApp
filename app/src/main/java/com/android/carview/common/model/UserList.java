package com.android.carview.common.model;

import com.google.gson.annotations.SerializedName;

public class UserList
{
    @SerializedName("id")
    public String id;
    @SerializedName("username")
    public String username;
    @SerializedName("email")
    public String email;
    @SerializedName("Password")
    public String password;
    @SerializedName("location")
    public String location;
    @SerializedName("is_admin")
    public String is_admin;
    @SerializedName("is_bloked")
    public int is_bloked;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public int getIs_blocked() {
        return is_bloked;
    }

    public void setIs_blocked(int is_blocked) {
        this.is_bloked = is_blocked;
    }
}
