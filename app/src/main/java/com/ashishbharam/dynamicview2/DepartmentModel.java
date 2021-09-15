package com.ashishbharam.dynamicview2;

public class DepartmentModel {
    private String departmentName;
    private String employeeEmail;
    private String employeeStatus;

    public DepartmentModel(String departmentName, String employeeEmail, String employeeStatus) {
        this.departmentName = departmentName;
        this.employeeEmail = employeeEmail;
        this.employeeStatus = employeeStatus;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
