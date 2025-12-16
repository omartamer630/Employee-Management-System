package com.employeemanagementsystem.patterns.decorator;

import com.employeemanagementsystem.model.Employee;

/**
 * Decorator that adds bonus compensation to an employee's salary.
 * Implements the Decorator Pattern to dynamically add bonus functionality.
 */
public class BonusDecorator extends EmployeeDecorator {
    private double bonusAmount;
    private String bonusReason;

    /**
     * Constructs a BonusDecorator wrapping an employee.
     *
     * @param employee The employee to decorate
     * @param bonusAmount The bonus amount to add to salary
     * @param bonusReason The reason for the bonus
     */
    public BonusDecorator(Employee employee, double bonusAmount, String bonusReason) {
        super(employee);
        this.bonusAmount = bonusAmount;
        this.bonusReason = bonusReason;
    }

    /**
     * Calculates the total salary including the bonus.
     * Uses the decorated employee's base salary and adds the bonus amount.
     *
     * @return The base salary plus bonus amount
     */
    @Override
    public double calculateSalary() {
        // Get base salary from the decorated employee
        double baseSalary = decoratedEmployee.getBaseSalary();
        return baseSalary + bonusAmount;
    }

    /**
     * Returns the benefits string with bonus information appended.
     *
     * @return The benefits including bonus details
     */
    @Override
    public String getBenefits() {
        String baseBenefits = decoratedEmployee.getBenefits();
        String bonusInfo = String.format("Bonus: $%.2f (%s)", bonusAmount, bonusReason);

        if (baseBenefits == null || baseBenefits.isEmpty()) {
            return bonusInfo;
        }
        return baseBenefits + ", " + bonusInfo;
    }

    /**
     * Gets the bonus amount.
     *
     * @return The bonus amount
     */
    public double getBonusAmount() {
        return this.bonusAmount;
    }

    /**
     * Gets the reason for the bonus.
     *
     * @return The bonus reason
     */
    public String getBonusReason() {
        return this.bonusReason;
    }
}