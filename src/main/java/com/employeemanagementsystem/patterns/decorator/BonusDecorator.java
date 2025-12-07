package com.employeemanagementsystem.patterns.decorator;

import com.employeemanagementsystem.model.Employee;

/**
 * Concrete Decorator - Adds bonus to employee salary
 * This decorator adds a performance bonus to the employee's salary
 */
public class BonusDecorator extends EmployeeDecorator {
    private double bonusAmount;
    private String bonusReason;

    public BonusDecorator(Employee employee, double bonusAmount, String bonusReason) {
        super(employee);
        this.bonusAmount = bonusAmount;
        this.bonusReason = bonusReason;
    }

    @Override
    public double calculateSalary() {
        // TODO: Implement bonus calculation
        // Get the base salary from decorated employee
        // Add the bonus amount
        // Return the total

        return 0.0;
    }

    @Override
    public String getBenefits() {
        // TODO: Add bonus information to benefits
        // Get the base benefits from decorated employee
        // Append bonus information (amount and reason)
        // Return the enhanced benefits string

        return "";
    }

    public double getBonusAmount() {
        return bonusAmount;
    }

    public String getBonusReason() {
        return bonusReason;
    }
}
