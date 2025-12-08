package com.employeemanagementsystem.database;

import com.employeemanagementsystem.model.*;
import com.employeemanagementsystem.patterns.singleton.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Employee operations
 * Handles all database CRUD operations for employees
 */
public class EmployeeDAO {

    /**
     * Insert a new employee into the database
     */
    public boolean insertEmployee(Employee employee) {
        String sql = "INSERT INTO employees (employee_id, first_name, last_name, email, " +
                "phone_number, hire_date, department, base_salary, employee_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employee.getEmployeeId());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhoneNumber());
            pstmt.setDate(6, Date.valueOf(employee.getHireDate()));
            pstmt.setString(7, employee.getDepartment());
            pstmt.setDouble(8, employee.getBaseSalary());
            pstmt.setString(9, employee.getEmployeeType());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting employee: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get all employees from the database
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        System.out.println("Executing query: " + sql); // Debug

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("ResultSet obtained: " + (rs != null)); // Debug

            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
                String dept = rs.getString("department");
                double salary = rs.getDouble("base_salary");
                String type = rs.getString("employee_type");

                // Create appropriate employee type
                Employee emp;
                if (type.equalsIgnoreCase("Full-time")) {
                    emp = new FullTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, dept, salary, 20);
                } else if (type.equalsIgnoreCase("Part-time")) {
                    emp = new PartTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, dept, salary, 20, 15.0);
                } else {
                    emp = new Contractor(id, firstName, lastName, email, phone,
                            hireDate, dept, salary,
                            LocalDate.now().plusYears(1), "Project");
                }
                employees.add(emp);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching employees: " + e.getMessage());
        }

        return employees;
    }

    /**
     * Get employee by ID
     */
    public Employee getEmployeeById(int employeeId) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        System.out.println("Executing query: " + sql); // Debug

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("ResultSet obtained: " + (rs != null)); // Debug

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
                String dept = rs.getString("department");
                double salary = rs.getDouble("base_salary");
                String type = rs.getString("employee_type");

                if (type.equalsIgnoreCase("Full-time")) {
                    return new FullTimeEmployee(employeeId, firstName, lastName, email,
                            phone, hireDate, dept, salary, 20);
                } else if (type.equalsIgnoreCase("Part-time")) {
                    return new PartTimeEmployee(employeeId, firstName, lastName, email,
                            phone, hireDate, dept, salary, 20, 15.0);
                } else {
                    return new Contractor(employeeId, firstName, lastName, email, phone,
                            hireDate, dept, salary,
                            LocalDate.now().plusYears(1), "Project");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching employee: " + e.getMessage());
        }

        return null;
    }

    /**
     * Update an existing employee
     */
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, " +
                "phone_number = ?, department = ?, base_salary = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhoneNumber());
            pstmt.setString(5, employee.getDepartment());
            pstmt.setDouble(6, employee.getBaseSalary());
            pstmt.setInt(7, employee.getEmployeeId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete an employee
     */
    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            return false;
        }
    }

    /**
     * Search employees by name or department
     */
    public List<Employee> searchEmployees(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE first_name LIKE ? OR last_name LIKE ? " +
                "OR department LIKE ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
                String dept = rs.getString("department");
                double salary = rs.getDouble("base_salary");
                String type = rs.getString("employee_type");

                Employee emp;
                if (type.equalsIgnoreCase("Full-time")) {
                    emp = new FullTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, dept, salary, 20);
                } else if (type.equalsIgnoreCase("Part-time")) {
                    emp = new PartTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, dept, salary, 20, 15.0);
                } else {
                    emp = new Contractor(id, firstName, lastName, email, phone,
                            hireDate, dept, salary,
                            LocalDate.now().plusYears(1), "Project");
                }
                employees.add(emp);
            }

        } catch (SQLException e) {
            System.err.println("Error searching employees: " + e.getMessage());
        }

        return employees;
    }
}