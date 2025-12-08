package com.employeemanagementsystem.patterns.factory;

import com.employeemanagementsystem.model.*;
import java.time.LocalDate;

/**
 * Factory Pattern - Employee Factory
 * Creates different types of employees based on the specified type
 */
public class EmployeeFactory {

    /**
     * Factory method that returns different employee objects based on the type specified
     * @param type Employee type: "fulltime", "parttime", or "contractor"
     * @param employeeId Unique employee ID
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param email Employee's email
     * @param phoneNumber Employee's phone number
     * @param hireDate Date when employee was hired
     * @param department Department object
     * @param baseSalary Base salary amount
     * @param additionalParam1 Extra parameter (leave days for fulltime, hours for parttime, end date for contractor)
     * @param additionalParam2 Extra parameter (unused for fulltime, hourly rate for parttime, project name for contractor)
     * @return Employee object of the appropriate type
     */
    public static Employee createEmployee(String type, int employeeId, String firstName, String lastName,
                                          String email, String phoneNumber, LocalDate hireDate,
                                          Department department, double baseSalary,
                                          Object additionalParam1, Object additionalParam2) {

        // Convert type string to lowercase for case-insensitive comparison
        String typeLower = type.toLowerCase();

        if (typeLower.contains("full") || typeLower.equals("full-time")) {
            // Full-time employee
            int annualLeaveDays = 20; // Default value
            if (additionalParam1 instanceof Integer) {
                annualLeaveDays = (Integer) additionalParam1;
            }
            return new FullTimeEmployee(employeeId, firstName, lastName, email, phoneNumber,
                    hireDate, department, baseSalary, annualLeaveDays);

        } else if (typeLower.contains("part") || typeLower.equals("part-time")) {
            // Part-time employee
            int hoursPerWeek = 20; // Default value
            double hourlyRate = 15.0; // Default value

            if (additionalParam1 instanceof Integer) {
                hoursPerWeek = (Integer) additionalParam1;
            }
            if (additionalParam2 instanceof Double) {
                hourlyRate = (Double) additionalParam2;
            }

            return new PartTimeEmployee(employeeId, firstName, lastName, email, phoneNumber,
                    hireDate, department, baseSalary, hoursPerWeek, hourlyRate);

        } else if (typeLower.contains("contract") || typeLower.equals("contractor")) {
            // Contractor employee
            LocalDate contractEndDate = LocalDate.now().plusYears(1); // Default: 1 year from now
            String projectName = "Default Project"; // Default value

            if (additionalParam1 instanceof LocalDate) {
                contractEndDate = (LocalDate) additionalParam1;
            }
            if (additionalParam2 instanceof String) {
                projectName = (String) additionalParam2;
            }

            return new Contractor(employeeId, firstName, lastName, email, phoneNumber,
                    hireDate, department, baseSalary, contractEndDate, projectName);

        } else {
            throw new IllegalArgumentException("Invalid employee type: " + type +
                    ". Valid types are: Full-time, Part-time, Contractor");
        }
    }

    /**
     * Overloaded method with default values for common parameters
     */
    public static Employee createEmployee(String type, int employeeId, String firstName, String lastName,
                                          Department department) {
        return createEmployee(type, employeeId, firstName, lastName,
                firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com",
                "555-0100", LocalDate.now(), department, 5000.0,
                type.toLowerCase().contains("full") ? 20 :
                        type.toLowerCase().contains("part") ? 20 : LocalDate.now().plusYears(1),
                type.toLowerCase().contains("part") ? 15.0 : "Default Project");
    }

    /**
     * Creates an employee with a randomly generated ID
     */
    public static Employee createEmployeeWithRandomId(String type, String firstName, String lastName,
                                                      Department department) {
        int randomId = (int)(Math.random() * 10000) + 1000;
        return createEmployee(type, randomId, firstName, lastName, department);
    }
}