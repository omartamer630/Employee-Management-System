package com.employeemanagementsystem.patterns.factory;

import com.employeemanagementsystem.model.Department;

/**
 * FACTORY PATTERN - Department Factory
 * Creates department objects with predefined configurations
 */
public class DepartmentFactory {

    public static Department createDepartment(String departmentType, int departmentId) {
        String normalizedType = departmentType.toLowerCase().trim();

        if (normalizedType.equals("hr")) {
            return new Department(departmentId, "Human Resources", "HR Manager", "Building A, Floor 1");
        }

        if (normalizedType.equals("finance")) {
            return new Department(departmentId, "Finance", "Finance Manager", "Building A, Floor 2");
        }

        if (normalizedType.equals("it")) {
            return new Department(departmentId, "Information Technology", "IT Manager", "Building B, Floor 3");
        }

        if (normalizedType.equals("sales")) {
            return new Department(departmentId, "Sales", "Sales Manager", "Building A, Floor 3");
        }

        if (normalizedType.equals("operations")) {
            return new Department(departmentId, "Operations", "Operations Manager", "Building C, Floor 1");
        }
        // uses throw
        throw new IllegalArgumentException("Invalid department type: " + departmentType);
    }

    public static Department createCustomDepartment(int departmentId, String departmentName,
                                                    String managerName, String location) {
        return new Department(departmentId, departmentName, managerName, location);
    }
}