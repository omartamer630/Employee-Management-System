package com.employeemanagementsystem.patterns.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SINGLETON PATTERN - Database Connection Manager
 * Ensures only one database connection instance exists throughout the application
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

            // Step 1: Connect without database
            connection = DriverManager.getConnection(URL_WITHOUT_DB, USERNAME, PASSWORD);

            // Step 2: Create database if not exists
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                System.out.println("Database checked/created successfully!");
            }

            // Step 3: Reconnect with the database
            connection.close(); // close old connection
            connection = DriverManager.getConnection(URL_WITH_DB, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
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
