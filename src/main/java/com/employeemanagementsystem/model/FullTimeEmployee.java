package com.employeemanagementsystem.model;

import java.time.LocalDate;

/**
 * Full-time Employee class
 * Includes benefits like health insurance, paid leave, etc.
 */
public class FullTimeEmployee extends Employee {
    private int annualLeaveDays;

    public FullTimeEmployee(int employeeId, String firstName, String lastName, String email,
                            String phoneNumber, LocalDate hireDate, Department department, // Changed
                            double baseSalary, int annualLeaveDays) {
        super(employeeId, firstName, lastName, email, phoneNumber, hireDate,
                department, baseSalary, "Full-time"); // Changed
        this.annualLeaveDays = annualLeaveDays;
    }


    @Override
    public double calculateSalary() {
        // Full-time employees get their full base salary
        return getBaseSalary();
    }

    @Override
    public String getBenefits() {
        return "Health Insurance, Pension Plan, " + annualLeaveDays + " Annual Leave Days";
    }

    public int getAnnualLeaveDays() {
        return annualLeaveDays;
    }

    public void setAnnualLeaveDays(int annualLeaveDays) {
        this.annualLeaveDays = annualLeaveDays;
    }
}
