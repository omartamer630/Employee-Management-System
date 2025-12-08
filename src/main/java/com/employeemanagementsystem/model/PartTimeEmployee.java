package com.employeemanagementsystem.model;

import java.time.LocalDate;

/**
 * Part-time Employee class
 * Salary calculated based on hours worked
 */
public class PartTimeEmployee extends Employee {
    private int hoursPerWeek;
    private double hourlyRate;

    public PartTimeEmployee(int employeeId, String firstName, String lastName, String email,
                            String phoneNumber, LocalDate hireDate, Department department,
                            double baseSalary, int hoursPerWeek, double hourlyRate) {
        super(employeeId, firstName, lastName, email, phoneNumber, hireDate,
                department, baseSalary, "Part-time");
        this.hoursPerWeek = hoursPerWeek;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        // Calculate monthly salary: hourly rate * hours per week * 4 weeks
        return hourlyRate * hoursPerWeek * 4;
    }

    @Override
    public String getBenefits() {
        return "Flexible Schedule, Limited Leave Days";
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
