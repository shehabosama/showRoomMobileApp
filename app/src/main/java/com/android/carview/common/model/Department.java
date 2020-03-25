package com.android.carview.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Department {


    private String dept_name;

    private int dept_image;

    public Department(String dept_name, int dept_image) {
        this.dept_name = dept_name;
        this.dept_image = dept_image;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public int getDept_image() {
        return dept_image;
    }

    public void setDept_image(int dept_image) {
        this.dept_image = dept_image;
    }
}
