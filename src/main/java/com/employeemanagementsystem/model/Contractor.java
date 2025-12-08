package com.employeemanagementsystem.model;

import java.time.LocalDate;

/**
 * Contractor class
 * Fixed contract duration and project-based salary
 */
public class Contractor extends Employee {
    private LocalDate contractEndDate;
    private String projectName;

    public Contractor(int employeeId, String firstName, String lastName, String email,
                      String phoneNumber, LocalDate hireDate, Department department,
                      double baseSalary, LocalDate contractEndDate, String projectName) {
        super(employeeId, firstName, lastName, email, phoneNumber, hireDate,
                department, baseSalary, "Contractor");
        this.contractEndDate = contractEndDate;
        this.projectName = projectName;
    }

    @Override
    public double calculateSalary() {
        // Contractors receive their project-based salary
        return getBaseSalary();
    }

    @Override
    public String getBenefits() {
        return "Project: " + projectName + ", Contract until: " + contractEndDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}