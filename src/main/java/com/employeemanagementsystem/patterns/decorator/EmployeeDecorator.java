
package com.employeemanagementsystem.patterns.decorator;

import com.employeemanagementsystem.model.Employee;
import java.time.LocalDate;

/**
 * DECORATOR PATTERN - Base Employee Decorator
 * This pattern allows us to add additional responsibilities/benefits to employees dynamically
 * without modifying the original employee classes
 * Useful for adding bonuses, special benefits, certifications, etc.
 */
public abstract class EmployeeDecorator extends Employee {
    protected Employee decoratedEmployee;

    /**
     * Constructor that wraps an existing employee
     */
    public EmployeeDecorator(Employee employee) {
        super(employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                employee.getDepartment(),
                employee.getBaseSalary(),
                employee.getEmployeeType());
        this.decoratedEmployee = employee;
    }

    @Override
    public double calculateSalary() {
        return decoratedEmployee.calculateSalary();
    }

    @Override
    public String getBenefits() {
        return decoratedEmployee.getBenefits();
    }
}