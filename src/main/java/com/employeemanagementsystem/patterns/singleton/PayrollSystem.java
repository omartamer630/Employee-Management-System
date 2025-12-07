package com.employeemanagementsystem.patterns.singleton;

import com.employeemanagementsystem.model.Employee;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * SINGLETON PATTERN - Payroll System
 * Ensures centralized and consistent payroll processing
 * Only one payroll system should exist to maintain consistency
 */
public class PayrollSystem {
    // Single instance
    private static PayrollSystem instance;

    // Store payroll records: Employee ID -> Total Paid
    private Map<Integer, Double> payrollRecords;

    /**
     * Private constructor to prevent external instantiation
     */
    private PayrollSystem() {
        this.payrollRecords = new HashMap<>();
        System.out.println("Payroll System initialized.");
    }

    /**
     * Get the single instance of PayrollSystem
     * Thread-safe implementation
     */
    public static synchronized PayrollSystem getInstance() {
        // TODO: Implement Singleton pattern
        // Check if instance exists, create if not, return existing instance
        // This ensures all payroll operations go through the same system

        return instance;
    }

    /**
     * Process payroll for an employee
     */
    public void processPayroll(Employee employee) {
        // TODO: Implement payroll processing logic
        // 1. Calculate the employee's salary using calculateSalary() method
        // 2. Update the payrollRecords map with employee ID and salary
        // 3. Print a confirmation message
        // 4. You might want to also save this to the database

    }

    /**
     * Get total amount paid to an employee
     */
    public double getTotalPaid(int employeeId) {
        // TODO: Implement logic to retrieve total paid amount
        // Return the amount from payrollRecords map
        // If employee not found, return 0.0

        return 0.0;
    }

    /**
     * Generate payroll report for all employees
     */
    public String generatePayrollReport() {
        // TODO: Implement payroll report generation
        // Create a formatted string showing all employees and their payments
        // Include total payroll amount

        return "";
    }

    /**
     * Clear all payroll records (for testing or reset)
     */
    public void clearRecords() {
        payrollRecords.clear();
        System.out.println("Payroll records cleared.");
    }

    /**
     * Get all payroll records
     */
    public Map<Integer, Double> getAllRecords() {
        return new HashMap<>(payrollRecords);
    }
}