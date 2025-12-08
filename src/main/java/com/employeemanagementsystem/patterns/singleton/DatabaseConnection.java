package com.employeemanagementsystem.patterns.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SINGLETON PATTERN - Database Connection Manager
 * Ensures only one database connection instance exists throughout the application
 * Automatically creates database, tables, and sample data if missing
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String DB_NAME = "employee_management_db";
    private static final String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String URL_WITH_DB = "jdbc:mysql://localhost:3306/" + DB_NAME + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "khemu123456";

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 1: Connect without specifying database
            connection = DriverManager.getConnection(URL_WITHOUT_DB, USERNAME, PASSWORD);

            // Step 2: Create database if it doesn't exist
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                System.out.println("Database checked/created successfully!");
            }

            // Step 3: Connect to the actual database
            connection.close();
            connection = DriverManager.getConnection(URL_WITH_DB, USERNAME, PASSWORD);

            // Step 4: Create tables and insert sample data if not exists
            initializeTables();

            System.out.println("Database connection established successfully!");

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates tables and inserts sample data if not exists
     */
    private void initializeTables() {
        try (Statement stmt = connection.createStatement()) {

            // Departments table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS departments (
                    department_id INT PRIMARY KEY,
                    department_name VARCHAR(100) NOT NULL,
                    manager_name VARCHAR(100),
                    location VARCHAR(150)
                )
            """);

            // Employees table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS employees (
                    employee_id INT PRIMARY KEY,
                    first_name VARCHAR(50) NOT NULL,
                    last_name VARCHAR(50) NOT NULL,
                    email VARCHAR(100),
                    phone_number VARCHAR(20),
                    hire_date DATE NOT NULL,
                    department VARCHAR(100),
                    base_salary DECIMAL(10,2) NOT NULL,
                    employee_type VARCHAR(20) NOT NULL
                )
            """);

            // Payroll records table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS payroll_records (
                    record_id INT AUTO_INCREMENT PRIMARY KEY,
                    employee_id INT NOT NULL,
                    payment_date DATE NOT NULL,
                    amount DECIMAL(10,2) NOT NULL,
                    payment_type VARCHAR(50),
                    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
                )
            """);

            // Insert sample departments if table is empty
            stmt.executeUpdate("""
                INSERT INTO departments (department_id, department_name, manager_name, location)
                SELECT 1, 'Human Resources', 'HR Manager', 'Building A, Floor 1' FROM DUAL
                WHERE NOT EXISTS (SELECT * FROM departments WHERE department_id = 1);
            """);
            stmt.executeUpdate("""
                INSERT INTO departments (department_id, department_name, manager_name, location)
                SELECT 2, 'Finance', 'Finance Manager', 'Building A, Floor 2' FROM DUAL
                WHERE NOT EXISTS (SELECT * FROM departments WHERE department_id = 2);
            """);
            stmt.executeUpdate("""
                INSERT INTO departments (department_id, department_name, manager_name, location)
                SELECT 3, 'Information Technology', 'IT Manager', 'Building B, Floor 3' FROM DUAL
                WHERE NOT EXISTS (SELECT * FROM departments WHERE department_id = 3);
            """);
            stmt.executeUpdate("""
                INSERT INTO departments (department_id, department_name, manager_name, location)
                SELECT 4, 'Sales', 'Sales Manager', 'Building A, Floor 3' FROM DUAL
                WHERE NOT EXISTS (SELECT * FROM departments WHERE department_id = 4);
            """);
            stmt.executeUpdate("""
                INSERT INTO departments (department_id, department_name, manager_name, location)
                SELECT 5, 'Operations', 'Operations Manager', 'Building C, Floor 1' FROM DUAL
                WHERE NOT EXISTS (SELECT * FROM departments WHERE department_id = 5);
            """);

            // Insert sample employees if table is empty
            stmt.executeUpdate("""
                INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, department, base_salary, employee_type)
                SELECT 1, 'John', 'Doe', 'john.doe@company.com', '123-456-7890', '2023-01-15', 'IT', 6000.00, 'Full-time'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM employees WHERE employee_id = 1);
            """);
            stmt.executeUpdate("""
                INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, department, base_salary, employee_type)
                SELECT 2, 'Jane', 'Smith', 'jane.smith@company.com', '123-456-7891', '2023-02-20', 'HR', 5500.00, 'Full-time'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM employees WHERE employee_id = 2);
            """);
            stmt.executeUpdate("""
                INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, department, base_salary, employee_type)
                SELECT 3, 'Bob', 'Johnson', 'bob.j@company.com', '123-456-7892', '2023-03-10', 'Finance', 5800.00, 'Full-time'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM employees WHERE employee_id = 3);
            """);
            stmt.executeUpdate("""
                INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, department, base_salary, employee_type)
                SELECT 4, 'Alice', 'Williams', 'alice.w@company.com', '123-456-7893', '2023-04-05', 'IT', 3200.00, 'Part-time'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM employees WHERE employee_id = 4);
            """);
            stmt.executeUpdate("""
                INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, department, base_salary, employee_type)
                SELECT 5, 'Charlie', 'Brown', 'charlie.b@company.com', '123-456-7894', '2023-05-12', 'Sales', 7000.00, 'Contractor'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM employees WHERE employee_id = 5);
            """);

            // Insert sample payroll records if table is empty
            stmt.executeUpdate("""
                INSERT INTO payroll_records (employee_id, payment_date, amount, payment_type)
                SELECT 1, '2024-01-31', 6000.00, 'Monthly Salary'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM payroll_records WHERE employee_id = 1);
            """);
            stmt.executeUpdate("""
                INSERT INTO payroll_records (employee_id, payment_date, amount, payment_type)
                SELECT 2, '2024-01-31', 5500.00, 'Monthly Salary'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM payroll_records WHERE employee_id = 2);
            """);
            stmt.executeUpdate("""
                INSERT INTO payroll_records (employee_id, payment_date, amount, payment_type)
                SELECT 3, '2024-01-31', 5800.00, 'Monthly Salary'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM payroll_records WHERE employee_id = 3);
            """);
            stmt.executeUpdate("""
                INSERT INTO payroll_records (employee_id, payment_date, amount, payment_type)
                SELECT 4, '2024-01-31', 3200.00, 'Monthly Salary'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM payroll_records WHERE employee_id = 4);
            """);
            stmt.executeUpdate("""
                INSERT INTO payroll_records (employee_id, payment_date, amount, payment_type)
                SELECT 5, '2024-01-31', 7000.00, 'Project Payment'
                FROM DUAL WHERE NOT EXISTS (SELECT * FROM payroll_records WHERE employee_id = 5);
            """);

            System.out.println("Tables and sample data checked/created successfully!");

        } catch (SQLException e) {
            System.err.println("Error initializing tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
