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
                "phone_number, hire_date, department_id, base_salary, employee_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employee.getEmployeeId());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhoneNumber());
            pstmt.setDate(6, Date.valueOf(employee.getHireDate()));

            // Handle department_id
            if (employee.getDepartment() != null) {
                pstmt.setInt(7, employee.getDepartment().getDepartmentId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }

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
     * Get all employees from the database with department information
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = """
            SELECT e.*, d.department_id as dept_id, d.department_name, 
                   d.manager_name, d.location
            FROM employees e
            LEFT JOIN departments d ON e.department_id = d.department_id
            """;
        System.out.println("Executing query: " + sql);

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("ResultSet obtained: " + (rs != null));

            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
                double salary = rs.getDouble("base_salary");
                String type = rs.getString("employee_type");

                // Create Department object
                Department department = null;
                int deptId = rs.getInt("dept_id");
                if (!rs.wasNull()) {
                    String deptName = rs.getString("department_name");
                    String manager = rs.getString("manager_name");
                    String location = rs.getString("location");
                    department = new Department(deptId, deptName, manager, location);
                }

                // Create appropriate employee type
                Employee emp;
                if (type.equalsIgnoreCase("Full-time")) {
                    emp = new FullTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, department, salary, 20);
                } else if (type.equalsIgnoreCase("Part-time")) {
                    emp = new PartTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, department, salary, 20, 15.0);
                } else {
                    emp = new Contractor(id, firstName, lastName, email, phone,
                            hireDate, department, salary,
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
        String sql = """
            SELECT e.*, d.department_id as dept_id, d.department_name, 
                   d.manager_name, d.location
            FROM employees e
            LEFT JOIN departments d ON e.department_id = d.department_id
            WHERE e.employee_id = ?
            """;
        System.out.println("Executing query: " + sql);

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("ResultSet obtained: " + (rs != null));

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
                double salary = rs.getDouble("base_salary");
                String type = rs.getString("employee_type");

                // Create Department object
                Department department = null;
                int deptId = rs.getInt("dept_id");
                if (!rs.wasNull()) {
                    String deptName = rs.getString("department_name");
                    String manager = rs.getString("manager_name");
                    String location = rs.getString("location");
                    department = new Department(deptId, deptName, manager, location);
                }

                if (type.equalsIgnoreCase("Full-time")) {
                    return new FullTimeEmployee(employeeId, firstName, lastName, email,
                            phone, hireDate, department, salary, 20);
                } else if (type.equalsIgnoreCase("Part-time")) {
                    return new PartTimeEmployee(employeeId, firstName, lastName, email,
                            phone, hireDate, department, salary, 20, 15.0);
                } else {
                    return new Contractor(employeeId, firstName, lastName, email, phone,
                            hireDate, department, salary,
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
                "phone_number = ?, department_id = ?, base_salary = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhoneNumber());

            // Handle department_id
            if (employee.getDepartment() != null) {
                pstmt.setInt(5, employee.getDepartment().getDepartmentId());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }

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
     * Search employees by name or department name
     */
    public List<Employee> searchEmployees(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = """
            SELECT e.*, d.department_id as dept_id, d.department_name, 
                   d.manager_name, d.location
            FROM employees e
            LEFT JOIN departments d ON e.department_id = d.department_id
            WHERE e.first_name LIKE ? OR e.last_name LIKE ? 
               OR d.department_name LIKE ?
            """;

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
                double salary = rs.getDouble("base_salary");
                String type = rs.getString("employee_type");

                // Create Department object
                Department department = null;
                int deptId = rs.getInt("dept_id");
                if (!rs.wasNull()) {
                    String deptName = rs.getString("department_name");
                    String manager = rs.getString("manager_name");
                    String location = rs.getString("location");
                    department = new Department(deptId, deptName, manager, location);
                }

                Employee emp;
                if (type.equalsIgnoreCase("Full-time")) {
                    emp = new FullTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, department, salary, 20);
                } else if (type.equalsIgnoreCase("Part-time")) {
                    emp = new PartTimeEmployee(id, firstName, lastName, email, phone,
                            hireDate, department, salary, 20, 15.0);
                } else {
                    emp = new Contractor(id, firstName, lastName, email, phone,
                            hireDate, department, salary,
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