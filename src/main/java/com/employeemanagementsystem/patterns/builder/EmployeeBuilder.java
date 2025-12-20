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
    private Department department = new Department(
            1,
            "Human Resources",
            "HR Manager",
            "Building A, Floor 1"
    );

    private double baseSalary = 200;
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

    public EmployeeBuilder email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public EmployeeBuilder hireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public EmployeeBuilder department(Department department) {
        this.department = department;
        return this;
    }

    public EmployeeBuilder baseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
        return this;
    }

    public EmployeeBuilder employeeType(String employeeType) {
        this.employeeType = employeeType.toLowerCase();
        return this;
    }

    public EmployeeBuilder annualLeaveDays(int annualLeaveDays) {
        this.annualLeaveDays = annualLeaveDays;
        return this;
    }

    public EmployeeBuilder hoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
        return this;
    }

    public EmployeeBuilder hourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public EmployeeBuilder contractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
        return this;
    }

    public EmployeeBuilder projectName(String projectName) {
        this.projectName = projectName;
        return this;

    }
    /**
     * Build method - creates and returns the final Employee object
     */
    public Employee build() {

        String type = employeeType.toLowerCase();

        if (type.contains("full")) {
            return new FullTimeEmployee(
                    employeeId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    hireDate,
                    department,
                    baseSalary,
                    annualLeaveDays
            );
        }

        if (type.contains("part")) {
            return new PartTimeEmployee(
                    employeeId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    hireDate,
                    department,
                    baseSalary,
                    (int) hourlyRate,
                    hoursPerWeek
            );
        }

        if (type.contains("contract")) {
            return new Contractor(
                    employeeId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    hireDate,
                    department,
                    baseSalary,
                    contractEndDate,
                    projectName
            );
        }

        // Default fallback: Full time
        return new FullTimeEmployee(
                employeeId,
                firstName,
                lastName,
                email,
                phoneNumber,
                hireDate,
                department,
                baseSalary,
                annualLeaveDays
        );
    }
}
