package com.employeemanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Main Application class for Employee Management System
 * Entry point for the JavaFX application
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/com/employeemanagementsystem/MainView.fxml"))
            );

            Scene scene = new Scene(root);
            primaryStage.setTitle("Employee Management System - Design Patterns Project");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            primaryStage.show();

            System.out.println("Application started successfully!");

        } catch (Exception e) {
            System.err.println("Error loading application: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void stop() {
        try {
            com.employeemanagementsystem.patterns.singleton.DatabaseConnection
                    .getInstance()
                    .closeConnection();
            System.out.println("Application closed successfully.");
        } catch (Exception e) {
            System.err.println("Error closing application: " + e.getMessage());
        }
    }

    /**
     * Main method - entry point
     */
    public static void main(String[] args) {
        launch(args);
    }
}