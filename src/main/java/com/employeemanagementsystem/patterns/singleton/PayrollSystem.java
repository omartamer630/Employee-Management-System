package com.employeemanagementsystem.patterns.singleton;

import com.employeemanagementsystem.model.Employee;

import java.time.LocalDate;
import java.util.*;

/**
 * SINGLETON PATTERN - Payroll System
 * Ensures centralized and consistent payroll processing
 */
public class PayrollSystem {

    private static PayrollSystem instance;

    // Employee ID -> Total Paid Amount (all-time)
    private final Map<Integer, Double> totalPayrollRecords;

    // Employee ID -> Last processed date
    private final Map<Integer, LocalDate> lastProcessedDate;

    // Employee ID -> Map<Date, Amount> for daily payroll history
    private final Map<Integer, Map<LocalDate, Double>> payrollHistory;

    private PayrollSystem() {
        this.totalPayrollRecords = new HashMap<>();
        this.lastProcessedDate = new HashMap<>();
        this.payrollHistory = new HashMap<>();
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
        int empId = employee.getEmployeeId();
        LocalDate today = LocalDate.now();

        // Prevent double payroll in a single day
        if (today.equals(lastProcessedDate.get(empId))) {
            System.out.println("Payroll already processed for " + employee.getFirstName() + " today.");
            return;
        }

        double salary = employee.calculateSalary();

        // Update all-time total
        totalPayrollRecords.put(empId,
                totalPayrollRecords.getOrDefault(empId, 0.0) + salary
        );

        // Update daily history
        payrollHistory.putIfAbsent(empId, new HashMap<>());
        payrollHistory.get(empId).put(today, salary);

        // Update last processed date
        lastProcessedDate.put(empId, today);

        System.out.println(
                "Payroll processed for " + employee.getFirstName() + " " + employee.getLastName()
                        + " | Paid: $" + salary
                        + " | Date: " + today
        );
    }

    /**
     * Get total amount paid to an employee (all-time)
     */
    public double getTotalPaid(int employeeId) {
        return totalPayrollRecords.getOrDefault(employeeId, 0.0);
    }

    /**
     * Generate payroll report (all-time totals)
     */
    public String generatePayrollReport() {
        if (totalPayrollRecords.isEmpty()) {
            return "No payroll has been processed yet.";
        }

        StringBuilder report = new StringBuilder();
        report.append("===== Payroll Report =====\n");

        double totalAll = 0.0;

        for (Map.Entry<Integer, Double> entry : totalPayrollRecords.entrySet()) {
            report.append("Employee ID: ").append(entry.getKey())
                    .append(" | Total Paid: $").append(entry.getValue()).append("\n");
            totalAll += entry.getValue();
        }

        report.append("--------------------------\n");
        report.append("Total Payroll Paid: $").append(totalAll).append("\n");

        return report.toString();
    }

    /**
     * Generate daily payroll report
     */
    public String generateDailyReport(LocalDate date) {
        StringBuilder report = new StringBuilder();
        report.append("===== Daily Payroll Report: ").append(date).append(" =====\n");

        double total = 0.0;
        for (Map.Entry<Integer, Map<LocalDate, Double>> entry : payrollHistory.entrySet()) {
            Double amount = entry.getValue().get(date);
            if (amount != null) {
                report.append("Employee ID: ").append(entry.getKey())
                        .append(" | Paid: $").append(amount).append("\n");
                total += amount;
            }
        }

        report.append("--------------------------\n");
        report.append("Total Payroll Paid Today: $").append(total).append("\n");

        return report.toString();
    }

    /**
     * Clear all payroll data
     */
    public void clearRecords() {
        totalPayrollRecords.clear();
        lastProcessedDate.clear();
        payrollHistory.clear();
        System.out.println("Payroll records cleared.");
    }

    /**
     * Retrieve all-time payroll records
     */
    public Map<Integer, Double> getAllRecords() {
        return new HashMap<>(totalPayrollRecords);
    }

    /**
     * Retrieve daily payroll records
     */
    public Map<Integer, Map<LocalDate, Double>> getPayrollHistory() {
        Map<Integer, Map<LocalDate, Double>> copy = new HashMap<>();
        payrollHistory.forEach((id, history) -> copy.put(id, new HashMap<>(history)));
        return copy;
    }
}
