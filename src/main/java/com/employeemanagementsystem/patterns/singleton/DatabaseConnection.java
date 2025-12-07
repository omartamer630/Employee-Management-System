package com.employeemanagementsystem.patterns.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SINGLETON PATTERN - Database Connection Manager
 * Ensures only one database connection instance exists throughout the application
 * This provides centralized database access and prevents multiple connections
 */
public class DatabaseConnection {
    // Single instance of the class
    private static DatabaseConnection instance;
    private Connection connection;

    // Database credentials
    private static final String URL =
            "jdbc:mysql://localhost:3306/employee_management_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "khemu123456"; // Your Docker MySQL password

    /**
     * Private constructor to prevent instantiation from outside
     * This is key to Singleton pattern - no one can create new instances
     */
    private DatabaseConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Public method to get the single instance of DatabaseConnection
     * Thread-safe using synchronized keyword
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get the actual database connection object
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Close the database connection
     */
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
