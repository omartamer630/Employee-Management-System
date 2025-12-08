package com.employeemanagementsystem.patterns.factory;

import com.employeemanagementsystem.model.*;
import java.time.LocalDate;

/**
 * FACTORY PATTERN - Employee Factory
 * Creates different types of employees (Full-time, Part-time, Contractor)
 * without exposing the creation logic to the client
 * This promotes loose coupling and makes it easy to add new employee types
 */
public class EmployeeFactory {

    /**
     * Create an employee based on the type specified
     * This is the factory method that returns different employee objects
     *
     * @param type Employee type: "fulltime", "parttime", or "contractor"
     * @param employeeId Unique employee ID
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param email Employee's email
     * @param phoneNumber Employee's phone number
     * @param hireDate Date when employee was hired
     * @param department Department name
     * @param baseSalary Base salary amount
     * @param additionalParam1 Extra parameter (leave days for fulltime, hours for parttime, end date for contractor)
     * @param additionalParam2 Extra parameter (unused for fulltime, hourly rate for parttime, project name for contractor)
     * @return Employee object of the appropriate type
     */
    public static Employee createEmployee(String type, int employeeId, String firstName,
                                          String lastName, String email, String phoneNumber,
                                          LocalDate hireDate, String department, double baseSalary,
                                          Object additionalParam1, Object additionalParam2) {
        String normalizedType = type.toLowerCase().trim();

        if (normalizedType.contains("full")) {
            int annualLeaveDays = (additionalParam1 != null) ? (Integer) additionalParam1 : 20;
            return new FullTimeEmployee(employeeId, firstName, lastName, email, phoneNumber,
                    hireDate, department, baseSalary, annualLeaveDays);
        }
        // aasda
        if (normalizedType.contains("part")) {
            int hoursPerWeek = (additionalParam1 != null) ? (Integer) additionalParam1 : 20;
            double hourlyRate = (additionalParam2 != null) ? (Double) additionalParam2 : 15.0;
            return new PartTimeEmployee(employeeId, firstName, lastName, email, phoneNumber,
                    hireDate, department, baseSalary, hoursPerWeek, hourlyRate);
        }

        if (normalizedType.contains("contract")) {
            LocalDate contractEndDate = (additionalParam1 != null) ?
                    (LocalDate) additionalParam1 : LocalDate.now().plusYears(1);
            String projectName = (additionalParam2 != null) ?
                    (String) additionalParam2 : "General Project";
            return new Contractor(employeeId, firstName, lastName, email, phoneNumber,
                    hireDate, department, baseSalary, contractEndDate, projectName);
        }

        throw new IllegalArgumentException("Invalid employee type: " + type);
    }

    /**
     * Simplified factory method with preset values for quick employee creation
     * Useful for testing or default employee creation
     */
    public static Employee createDefaultEmployee(String type, String firstName, String lastName) {
        int randomId = (int)(Math.random() * 9000) + 1000;
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";

        return createEmployee(type, randomId, firstName, lastName, email, "555-0100",
                LocalDate.now(), "General", 5000.0,
                type.toLowerCase().contains("full") ? 20 :
                        type.toLowerCase().contains("part") ? 20 : LocalDate.now().plusYears(1),
                type.toLowerCase().contains("part") ? 15.0 : "Default Project");
    }
}