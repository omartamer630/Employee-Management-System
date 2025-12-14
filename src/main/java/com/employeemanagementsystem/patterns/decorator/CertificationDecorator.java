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
        return super.calculateSalary() + certificationAllowance;
    }

    @Override
    public String getBenefits() {
        // TODO: Add certification information to benefits
        // Get base benefits from decorated employee
        // Append certification name and allowance information
        String certificationBenefit = String.format(
                "Professional Certification: %s (Allowance: $%.2f)",
                certificationName,
                certificationAllowance
        );
        // Return the enhanced benefits string

        return super.getBenefits() + certificationBenefit;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public double getCertificationAllowance() {
        return certificationAllowance;
    }
}
