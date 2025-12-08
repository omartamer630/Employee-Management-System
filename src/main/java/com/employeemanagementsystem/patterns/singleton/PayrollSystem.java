package com.employeemanagementsystem.patterns.singleton;

import com.employeemanagementsystem.model.Employee;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * SINGLETON PATTERN - Payroll System
 * Ensures centralized and consistent payroll processing
 */
public class PayrollSystem {

    private static PayrollSystem instance;

    // Employee ID -> Total Paid Amount
    private final Map<Integer, Double> payrollRecords;

    private PayrollSystem() {
        this.payrollRecords = new HashMap<>();
        System.out.println("Payroll System initialized.");
    }

    /**
     * Singleton access method
     */
    public static synchronized PayrollSystem getInstance() {
        if (instance == null) {
            instance = new PayrollSystem();
        }
        return instance;
    }

    /**
     * Process payroll for an employee
     */
    public void processPayroll(Employee employee) {
        double salary = employee.calculateSalary(); // your Employee model must have this

        payrollRecords.put(
                employee.getEmployeeId(),
                payrollRecords.getOrDefault(employee.getEmployeeId(), 0.0) + salary
        );

        System.out.println(
                "Payroll processed for " + employee.getFirstName() + " "
                        + employee.getLastName() + " | Paid: $" + salary
                        + " | Date: " + LocalDate.now()
        );
    }

    /**
     * Get total amount paid to an employee
     */
    public double getTotalPaid(int employeeId) {
        return payrollRecords.getOrDefault(employeeId, 0.0);
    }

    /**
     * Generate payroll report
     */
    public String generatePayrollReport() {
        if (payrollRecords.isEmpty()) {
            return "No payroll has been processed yet.";
        }

        StringBuilder report = new StringBuilder();
        report.append("===== Payroll Report =====\n");

        double totalAll = 0.0;

        for (Map.Entry<Integer, Double> entry : payrollRecords.entrySet()) {
            report.append("Employee ID: ").append(entry.getKey())
                    .append(" | Total Paid: $").append(entry.getValue()).append("\n");

            totalAll += entry.getValue();
        }

        report.append("--------------------------\n");
        report.append("Total Payroll Paid: $").append(totalAll).append("\n");

        return report.toString();
    }

    /**
     * Clear all payroll data
     */
    public void clearRecords() {
        payrollRecords.clear();
        System.out.println("Payroll records cleared.");
    }

    /**
     * Retrieve all payroll records
     */
    public Map<Integer, Double> getAllRecords() {
        return new HashMap<>(payrollRecords);
    }
}
