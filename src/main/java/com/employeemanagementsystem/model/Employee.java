package com.employeemanagementsystem.model;

import java.time.LocalDate;

/**
 * Base Employee class representing an employee in the system
 * This is the foundation for all employee types
 */
public abstract class Employee implements Cloneable {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String department;
    private double baseSalary;
    private String employeeType; // "Full-time", "Part-time", "Contractor"

    /**
     * Constructor for Employee
     */
    public Employee(int employeeId, String firstName, String lastName, String email,
                    String phoneNumber, LocalDate hireDate, String department,
                    double baseSalary, String employeeType) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.department = department;
        this.baseSalary = baseSalary;
        this.employeeType = employeeType;
    }

    /**
     * Abstract method to calculate salary - different for each employee type
     */
    public abstract double calculateSalary();

    /**
     * Abstract method to get employee benefits
     */
    public abstract String getBenefits();

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    /**
     * Clone method for Prototype Pattern
     */
    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hireDate=" + hireDate +
                ", department='" + department + '\'' +
                ", baseSalary=" + baseSalary +
                ", employeeType='" + employeeType + '\'' +
                '}';
    }
}