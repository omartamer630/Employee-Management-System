package com.employeemanagementsystem.patterns.decorator;

import com.employeemanagementsystem.model.Employee;

/**
 * Decorator that adds overtime pay to an employee's salary.
 * Implements the Decorator Pattern to dynamically add overtime functionality.
 */
public class OvertimeDecorator extends EmployeeDecorator {
    private int overtimeHours;
    private double overtimeRate;

    /**
     * Constructs an OvertimeDecorator wrapping an employee.
     *
     * @param employee The employee to decorate
     * @param overtimeHours The number of overtime hours worked
     * @param overtimeRate The hourly rate for overtime pay
     */
    public OvertimeDecorator(Employee employee, int overtimeHours, double overtimeRate) {
        super(employee);
        this.overtimeHours = overtimeHours;
        this.overtimeRate = overtimeRate;
    }

    /**
     * Calculates the total salary including overtime pay.
     * Uses the decorated employee's base salary and adds overtime compensation.
     *
     * @return The base salary plus overtime pay (hours * rate)
     */
    @Override
    public double calculateSalary() {
        // Get base salary from the decorated employee
        double baseSalary = decoratedEmployee.getBaseSalary();
        double overtimePay = overtimeHours * overtimeRate;
        return baseSalary + overtimePay;
    }

    /**
     * Returns the benefits string with overtime information appended.
     *
     * @return The benefits including overtime details
     */
    @Override
    public String getBenefits() {
        String baseBenefits = decoratedEmployee.getBenefits();
        String overtimeInfo = String.format("Overtime: %d hours @ $%.2f/hr ($%.2f)",
                overtimeHours, overtimeRate, overtimeHours * overtimeRate);

        if (baseBenefits == null || baseBenefits.isEmpty()) {
            return overtimeInfo;
        }
        return baseBenefits + ", " + overtimeInfo;
    }

    /**
     * Gets the number of overtime hours.
     *
     * @return The overtime hours
     */
    public int getOvertimeHours() {
        return this.overtimeHours;
    }

    /**
     * Gets the overtime hourly rate.
     *
     * @return The overtime rate
     */
    public double getOvertimeRate() {
        return this.overtimeRate;
    }
}