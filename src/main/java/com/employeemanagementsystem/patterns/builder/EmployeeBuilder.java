package com.employeemanagementsystem.patterns.builder;

import com.employeemanagementsystem.model.*;
import java.time.LocalDate;

/**
 * BUILDER PATTERN - Employee Builder
 * Provides a flexible way to construct complex Employee objects step by step
 * Useful when an object has many parameters and you want to avoid telescoping constructors
 * This pattern improves code readability and allows optional parameters
 */
public class EmployeeBuilder {
    // Required parameters
    private int employeeId;
    private String firstName;
    private String lastName;

    // Optional parameters - initialized to default values
    private String email = "";
    private String phoneNumber = "";
    private LocalDate hireDate = LocalDate.now();
    private String department = "General";
    private double baseSalary = 0.0;
    private String employeeType = "fulltime";

    // Type-specific parameters
    private int annualLeaveDays = 20;
    private int hoursPerWeek = 20;
    private double hourlyRate = 15.0;
    private LocalDate contractEndDate = LocalDate.now().plusYears(1);
    private String projectName = "General Project";

    /**
     * Constructor with required parameters
     */
    public EmployeeBuilder(int employeeId, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Builder method for email
     */
    public EmployeeBuilder email(String email) {
        // TODO: Implement builder pattern method
        // Set the email field to the provided value
        // Return 'this' to allow method chaining

        return this;
    }

    /**
     * Builder method for phone number
     */
    public EmployeeBuilder phoneNumber(String phoneNumber) {
        // TODO: Implement builder pattern method
        // Set the phoneNumber field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for hire date
     */
    public EmployeeBuilder hireDate(LocalDate hireDate) {
        // TODO: Implement builder pattern method
        // Set the hireDate field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for department
     */
    public EmployeeBuilder department(String department) {
        // TODO: Implement builder pattern method
        // Set the department field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for base salary
     */
    public EmployeeBuilder baseSalary(double baseSalary) {
        // TODO: Implement builder pattern method
        // Set the baseSalary field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for employee type
     */
    public EmployeeBuilder employeeType(String employeeType) {
        // TODO: Implement builder pattern method
        // Set the employeeType field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for annual leave days (Full-time specific)
     */
    public EmployeeBuilder annualLeaveDays(int annualLeaveDays) {
        // TODO: Implement builder pattern method
        // Set the annualLeaveDays field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for hours per week (Part-time specific)
     */
    public EmployeeBuilder hoursPerWeek(int hoursPerWeek) {
        // TODO: Implement builder pattern method
        // Set the hoursPerWeek field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for hourly rate (Part-time specific)
     */
    public EmployeeBuilder hourlyRate(double hourlyRate) {
        // TODO: Implement builder pattern method
        // Set the hourlyRate field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for contract end date (Contractor specific)
     */
    public EmployeeBuilder contractEndDate(LocalDate contractEndDate) {
        // TODO: Implement builder pattern method
        // Set the contractEndDate field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Builder method for project name (Contractor specific)
     */
    public EmployeeBuilder projectName(String projectName) {
        // TODO: Implement builder pattern method
        // Set the projectName field
        // Return 'this' for method chaining

        return this;
    }

    /**
     * Build method - creates and returns the final Employee object
     * This is called last after setting all desired parameters
     */
    public Employee build() {

        // TODO: Implement the build method
        // Based on employeeType, create the appropriate employee object
        // Use the fields set via builder methods
        //
        // If employeeType contains "fulltime" or "full":
        //    - Create FullTimeEmployee with annualLeaveDays
        //
        // If employeeType contains "parttime" or "part":
        //    - Create PartTimeEmployee with hoursPerWeek and hourlyRate
        //
        // If employeeType contains "contractor" or "contract":
        //    - Create Contractor with contractEndDate and projectName
        //
        // Default to FullTimeEmployee if type doesn't match
        // Return the created employee object

        return null;
    }
}