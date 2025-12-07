package com.employeemanagementsystem.database;

import com.employeemanagementsystem.model.Department;
import com.employeemanagementsystem.patterns.singleton.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Department operations
 * Handles all database CRUD operations for departments
 */
public class DepartmentDAO {

    /**
     * Insert a new department
     */
    public boolean insertDepartment(Department department) {
        String sql = "INSERT INTO departments (department_id, department_name, manager_name, location) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, department.getDepartmentId());
            pstmt.setString(2, department.getDepartmentName());
            pstmt.setString(3, department.getManagerName());
            pstmt.setString(4, department.getLocation());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting department: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get all departments
     */
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("department_id");
                String name = rs.getString("department_name");
                String manager = rs.getString("manager_name");
                String location = rs.getString("location");

                Department dept = new Department(id, name, manager, location);
                departments.add(dept);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching departments: " + e.getMessage());
        }

        return departments;
    }

    /**
     * Get department by ID
     */
    public Department getDepartmentById(int departmentId) {
        String sql = "SELECT * FROM departments WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("department_name");
                String manager = rs.getString("manager_name");
                String location = rs.getString("location");

                return new Department(departmentId, name, manager, location);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching department: " + e.getMessage());
        }

        return null;
    }

    /**
     * Update a department
     */
    public boolean updateDepartment(Department department) {
        String sql = "UPDATE departments SET department_name = ?, manager_name = ?, " +
                "location = ? WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department.getDepartmentName());
            pstmt.setString(2, department.getManagerName());
            pstmt.setString(3, department.getLocation());
            pstmt.setInt(4, department.getDepartmentId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating department: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a department
     */
    public boolean deleteDepartment(int departmentId) {
        String sql = "DELETE FROM departments WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, departmentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting department: " + e.getMessage());
            return false;
        }
    }
}