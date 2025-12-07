package com.employeemanagementsystem.patterns.decorator;

import com.employeemanagementsystem.model.Employee;

/**
 * Concrete Decorator - Adds overtime pay
 * This decorator adds overtime compensation to employee salary
 */
public class OvertimeDecorator extends EmployeeDecorator {
    private int overtimeHours;
    private double overtimeRate;

    public OvertimeDecorator(Employee employee, int overtimeHours, double overtimeRate) {
        super(employee);
        this.overtimeHours = overtimeHours;
        this.overtimeRate = overtimeRate;
    }

    @Override
    public double calculateSalary() {
        // TODO: Implement salary calculation with overtime
        // Get base salary from decorated employee
        // Calculate overtime pay: overtimeHours * overtimeRate
        // Add overtime pay to base salary
        // Return the total

        return 0.0;
    }

    @Override
    public String getBenefits() {
        // TODO: Add overtime information to benefits
        // Get base benefits from decorated employee
        // Append overtime hours and rate information
        // Return the enhanced benefits string

        return "";
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public double getOvertimeRate() {
        return overtimeRate;
    }
}