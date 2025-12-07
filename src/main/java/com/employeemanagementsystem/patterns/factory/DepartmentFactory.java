package com.employeemanagementsystem.patterns.factory;

import com.employeemanagementsystem.model.Department;

/**
 * FACTORY PATTERN - Department Factory
 * Creates different department objects with predefined configurations
 * This centralizes department creation and ensures consistent initialization
 */
public class DepartmentFactory {

    /**
     * Create a department based on the department type
     * Each department type has predefined characteristics
     *
     * @param departmentType Type of department: "HR", "Finance", "IT", "Sales", "Operations"
     * @param departmentId Unique department ID
     * @return Department object with appropriate configuration
     */
    public static Department createDepartment(String departmentType, int departmentId) {

        // TODO: Implement Factory Pattern logic for departments
        // Based on departmentType, create Department objects with predefined settings
        //
        // If departmentType is "HR" or "hr":
        //    - Department name: "Human Resources"
        //    - Default manager: "HR Manager"
        //    - Location: "Building A, Floor 1"
        //
        // If departmentType is "Finance" or "finance":
        //    - Department name: "Finance"
        //    - Default manager: "Finance Manager"
        //    - Location: "Building A, Floor 2"
        //
        // If departmentType is "IT" or "it":
        //    - Department name: "Information Technology"
        //    - Default manager: "IT Manager"
        //    - Location: "Building B, Floor 3"
        //
        // If departmentType is "Sales" or "sales":
        //    - Department name: "Sales"
        //    - Default manager: "Sales Manager"
        //    - Location: "Building A, Floor 3"
        //
        // If departmentType is "Operations" or "operations":
        //    - Department name: "Operations"
        //    - Default manager: "Operations Manager"
        //    - Location: "Building C, Floor 1"
        //
        // If departmentType doesn't match, throw IllegalArgumentException
        // Return the created Department object

        return null;
    }

    /**
     * Create a custom department with all parameters specified
     * Use this when you need full control over department creation
     */
    public static Department createCustomDepartment(int departmentId, String departmentName,
                                                    String managerName, String location) {
        // TODO: Implement custom department creation
        // Simply create and return a new Department with the provided parameters
        // This allows flexibility for departments not covered by the preset types

        return null;
    }
}