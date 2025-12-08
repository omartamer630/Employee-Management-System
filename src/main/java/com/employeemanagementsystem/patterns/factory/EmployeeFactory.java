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

        // TODO: Implement Factory Pattern logic
        // Based on the 'type' parameter, create and return the appropriate employee object
        //
        // If type is "fulltime" or "full-time":
        //    - additionalParam1 should be cast to Integer (annual leave days)
        //    - Create and return a new FullTimeEmployee object
        //
        // If type is "parttime" or "part-time":
        //    - additionalParam1 should be cast to Integer (hours per week)
        //    - additionalParam2 should be cast to Double (hourly rate)
        //    - Create and return a new PartTimeEmployee object
        //
        // If type is "contractor":
        //    - additionalParam1 should be cast to LocalDate (contract end date)
        //    - additionalParam2 should be cast to String (project name)
        //    - Create and return a new Contractor object
        //
//         If type doesn't match any of the above, throw an IllegalArgumentException

        return null;
    }

    /**
     * Simplified factory method with preset values for quick employee creation
     * Useful for testing or default employee creation
     */
    public static Employee createDefaultEmployee(String type, String firstName, String lastName) {
        // TODO: Implement a simplified employee creation method
        // Generate a random employee ID
        // Set default values for email, phone, hire date (today), department ("General")
        // Set default base salary (5000 for example)
        // Set default additional parameters based on type
        // Call the main createEmployee method with these defaults

        return null;
    }
}