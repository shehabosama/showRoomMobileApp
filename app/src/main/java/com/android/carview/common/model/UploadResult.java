package com.android.carview.common.model;

import com.google.gson.annotations.SerializedName;

public class UploadResult {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
}
