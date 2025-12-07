package com.employeemanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Department class representing different departments in the organization
 * Used with Factory Pattern to create different department types
 */
public class Department {
    private int departmentId;
    private String departmentName;
    private String managerName;
    private String location;
    private List<Integer> employeeIds; // List of employee IDs in this department

    /**
     * Constructor for Department
     */
    public Department(int departmentId, String departmentName, String managerName, String location) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerName = managerName;
        this.location = location;
        this.employeeIds = new ArrayList<>();
    }

    /**
     * Add an employee to the department
     */
    public void addEmployee(int employeeId) {
        if (!employeeIds.contains(employeeId)) {
            employeeIds.add(employeeId);
        }
    }

    /**
     * Remove an employee from the department
     */
    public void removeEmployee(int employeeId) {
        employeeIds.remove(Integer.valueOf(employeeId));
    }

    /**
     * Get the number of employees in the department
     */
    public int getEmployeeCount() {
        return employeeIds.size();
    }

    // Getters and Setters
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Integer> getEmployeeIds() {
        return new ArrayList<>(employeeIds);
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = new ArrayList<>(employeeIds);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", managerName='" + managerName + '\'' +
                ", location='" + location + '\'' +
                ", employeeCount=" + employeeIds.size() +
                '}';
    }
}