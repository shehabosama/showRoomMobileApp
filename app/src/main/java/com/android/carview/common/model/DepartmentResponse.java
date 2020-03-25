package com.android.carview.common.model;

import java.util.ArrayList;
import java.util.List;

public class DepartmentResponse {

    List<Department> departmentList= new ArrayList<>();

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
