package com.employeemanagementsystem.patterns.decorator;

import com.employeemanagementsystem.model.Employee;

/**
 * Concrete Decorator - Adds certification benefits
 * This decorator adds professional certification benefits and recognition
 */
public class CertificationDecorator extends EmployeeDecorator {
    private String certificationName;
    private double certificationAllowance;

    public CertificationDecorator(Employee employee, String certificationName,
                                  double certificationAllowance) {
        super(employee);
        this.certificationName = certificationName;
        this.certificationAllowance = certificationAllowance;
    }

    @Override
    public double calculateSalary() {
        // TODO: Implement salary calculation with certification allowance
        // Get base salary from decorated employee
        // Add certification allowance
        // Return the total

        return 0.0;
    }

    @Override
    public String getBenefits() {
        // TODO: Add certification information to benefits
        // Get base benefits from decorated employee
        // Append certification name and allowance information
        // Return the enhanced benefits string

        return "";
    }

    public String getCertificationName() {
        return certificationName;
    }

    public double getCertificationAllowance() {
        return certificationAllowance;
    }
}
