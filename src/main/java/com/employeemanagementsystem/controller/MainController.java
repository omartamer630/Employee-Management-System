package com.employeemanagementsystem.controller;

import com.employeemanagementsystem.database.*;
import com.employeemanagementsystem.model.*;
import com.employeemanagementsystem.patterns.builder.EmployeeBuilder;
import com.employeemanagementsystem.patterns.decorator.*;
import com.employeemanagementsystem.patterns.factory.*;
import com.employeemanagementsystem.patterns.prototype.EmployeePrototypeRegistry;
import com.employeemanagementsystem.patterns.singleton.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

/**
 * Main Controller for the Employee Management System (Ali Hassan Ali)
 * Integrates all design patterns and manages the GUI
 */
public class MainController {

    // ==================== FXML Components (Ali Hassan Ali) ====================

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> colId;
    @FXML private TableColumn<Employee, String> colFirstName;
    @FXML private TableColumn<Employee, String> colLastName;
    @FXML private TableColumn<Employee, String> colEmail;
    @FXML private TableColumn<Employee, String> colDepartment;
    @FXML private TableColumn<Employee, String> colType;
    @FXML private TableColumn<Employee, Double> colSalary;

    @FXML private TextField txtEmployeeId;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private DatePicker dateHire;
    @FXML private TextField txtDepartment;
    @FXML private TextField txtSalary;
    @FXML private ComboBox<String> cmbEmployeeType;
    @FXML private TextField txtSearch;

    @FXML private Label lblStatus;
    @FXML private TextArea txtPayrollReport;

    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;
    private ObservableList<Employee> employeeList;

    /**
     * Initialize the controller (Ali Hassan Ali)
     */
    @FXML
    public void initialize() {
        // TODO: ALI HASSAN ALI - Initialize Singleton database connection
        // Call DatabaseConnection.getInstance() to establish connection
        DatabaseConnection.getInstance();

        employeeDAO = new EmployeeDAO();
        departmentDAO = new DepartmentDAO();

        // TODO: SHAHD AMR - Initialize prototype registry
        EmployeePrototypeRegistry.initializePrototypes();

        // Setup TableView columns
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colType.setCellValueFactory(new PropertyValueFactory<>("employeeType"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));

        cmbEmployeeType.setItems(FXCollections.observableArrayList(
                "Full-time", "Part-time", "Contractor"
        ));
        cmbEmployeeType.setValue("Full-time");

        loadEmployees();

        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        displayEmployeeDetails(newValue);
                    }
                }
        );
    }

    /**
     * Load all employees from database (Ali Hassan Ali)
     */
    private void loadEmployees() {
        employeeList = FXCollections.observableArrayList(employeeDAO.getAllEmployees());
        employeeTable.setItems(employeeList);
    }

    /**
     * Display selected employee details (Ali Hassan Ali)
     */
    private void displayEmployeeDetails(Employee employee) {
        txtEmployeeId.setText(String.valueOf(employee.getEmployeeId()));
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtEmail.setText(employee.getEmail());
        txtPhone.setText(employee.getPhoneNumber());
        dateHire.setValue(employee.getHireDate());
        txtDepartment.setText(employee.getDepartment());
        txtSalary.setText(String.valueOf(employee.getBaseSalary()));
        cmbEmployeeType.setValue(employee.getEmployeeType());
    }

    // ==================== OMAR TAMER - FACTORY PATTERN ====================

    /**
     * Add employee using EmployeeFactory (Omar Tamer)
     */
    @FXML
    private void handleAddEmployee() {
        try {
            int id = Integer.parseInt(txtEmployeeId.getText());
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            LocalDate hireDate = dateHire.getValue();
            String department = txtDepartment.getText();
            double salary = Double.parseDouble(txtSalary.getText());
            String type = cmbEmployeeType.getValue();

            // TODO: OMAR TAMER - Use EmployeeFactory.createEmployee()
            // For Full-time: additionalParam1 = 20 (annual leave days), additionalParam2 = null
            // For Part-time: additionalParam1 = 20 (hours/week), additionalParam2 = 15.0 (hourly rate)
            // For Contractor: additionalParam1 = LocalDate (end date), additionalParam2 = "Project Name"
            Employee newEmployee = null;

            if (newEmployee != null && employeeDAO.insertEmployee(newEmployee)) {
                loadEmployees();
                clearFields();
                lblStatus.setText("✓ Employee added using Factory Pattern!");
                lblStatus.setStyle("-fx-text-fill: green;");
            } else {
                lblStatus.setText("✗ Failed to add employee.");
                lblStatus.setStyle("-fx-text-fill: red;");
            }

        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Create department using DepartmentFactory (Omar Tamer)
     */
    @FXML
    private void handleCreateDepartment() {
        TextInputDialog dialog = new TextInputDialog("IT");
        dialog.setTitle("Create Department");
        dialog.setContentText("Enter type (HR/IT/Finance/Sales/Operations):");

        dialog.showAndWait().ifPresent(type -> {
            // TODO: OMAR TAMER - Use DepartmentFactory.createDepartment()
            // Generate random ID: int id = (int)(Math.random() * 1000) + 1;
            // Department dept = DepartmentFactory.createDepartment(type, id);
            // Then: departmentDAO.insertDepartment(dept);

            lblStatus.setText("✓ Department created using Factory Pattern!");
            lblStatus.setStyle("-fx-text-fill: green;");
        });
    }

    // ==================== FATMA MOHAMED - BUILDER PATTERN ====================

    /**
     * Add employee using EmployeeBuilder (Fatma Mohamed)
     */
    @FXML
    private void handleAddEmployeeWithBuilder() {
        try {
            int id = Integer.parseInt(txtEmployeeId.getText());
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();

            // TODO: FATMA MOHAMED - Use EmployeeBuilder with method chaining
            // Employee emp = new EmployeeBuilder(id, firstName, lastName)
            //     .email(txtEmail.getText())
            //     .phoneNumber(txtPhone.getText())
            //     .hireDate(dateHire.getValue())
            //     .department(txtDepartment.getText())
            //     .baseSalary(Double.parseDouble(txtSalary.getText()))
            //     .employeeType(cmbEmployeeType.getValue())
            //     .build();
            Employee newEmployee = null;

            if (newEmployee != null && employeeDAO.insertEmployee(newEmployee)) {
                loadEmployees();
                clearFields();
                lblStatus.setText("✓ Employee added using Builder Pattern!");
                lblStatus.setStyle("-fx-text-fill: green;");
            }

        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    // ==================== SHAHD AMR - PROTOTYPE PATTERN ====================

    /**
     * Clone employee using Prototype Pattern (Shahd Amr)
     */
    @FXML
    private void handleCloneEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee to clone.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // TODO: SHAHD AMR - Clone employee using one of these methods:
            //
            // Method 1: Direct clone
            // Employee clone = selected.clone();
            // clone.setEmployeeId((int)(Math.random() * 10000) + 1000);
            // clone.setFirstName("Copy of " + clone.getFirstName());
            //
            // Method 2: Use EmployeePrototypeRegistry
            // int newId = (int)(Math.random() * 10000) + 1000;
            // Employee clone = EmployeePrototypeRegistry.cloneAndCustomize(
            //     selected.getEmployeeType().toLowerCase(),
            //     newId, "Jane", "Doe", "jane@company.com", "IT"
            // );
            //
            // Then insert: employeeDAO.insertEmployee(clone);
            // loadEmployees();

            lblStatus.setText("✓ Employee cloned using Prototype Pattern!");
            lblStatus.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    // ==================== ABDELRAHMAN MOHAMED - SINGLETON PATTERN ====================

    /**
     * Process payroll using PayrollSystem Singleton (Abdelrahman Mohamed)
     */
    @FXML
    private void handleProcessPayroll() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        // TODO: ABDELRAHMAN MOHAMED - Use PayrollSystem Singleton
        // PayrollSystem payroll = PayrollSystem.getInstance();
        // payroll.processPayroll(selected);
        // double total = payroll.getTotalPaid(selected.getEmployeeId());
        // lblStatus.setText("✓ Payroll processed! Total: $" + total);

        lblStatus.setText("✓ Payroll processed using Singleton Pattern!");
        lblStatus.setStyle("-fx-text-fill: green;");
    }

    /**
     * Generate payroll report (Abdelrahman Mohamed)
     */
    @FXML
    private void handleGeneratePayrollReport() {
        // TODO: ABDELRAHMAN MOHAMED - Generate payroll report
        // PayrollSystem payroll = PayrollSystem.getInstance();
        // String report = payroll.generatePayrollReport();
        // txtPayrollReport.setText(report);

        txtPayrollReport.setText("Payroll Report Generated!");
        lblStatus.setText("✓ Report generated!");
        lblStatus.setStyle("-fx-text-fill: green;");
    }

    // ==================== ABDELRAHMAN MAGDY - DECORATOR PATTERN ====================

    /**
     * Apply bonus decorator (Abdelrahman Magdy)
     */
    @FXML
    private void handleApplyBonus() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // TODO: ABDELRAHMAN MAGDY - Use BonusDecorator
            // Employee empWithBonus = new BonusDecorator(selected, 1000.0, "Performance Bonus");
            // double newSalary = empWithBonus.calculateSalary();
            // String benefits = empWithBonus.getBenefits();
            // txtPayrollReport.setText("New Salary: $" + newSalary + "\n" + benefits);

            lblStatus.setText("✓ Bonus applied using Decorator Pattern!");
            lblStatus.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Apply overtime decorator (Abdelrahman Magdy)
     */
    @FXML
    private void handleApplyOvertime() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // TODO: ABDELRAHMAN MAGDY - Use OvertimeDecorator
            // Employee empWithOT = new OvertimeDecorator(selected, 10, 25.0);
            // double newSalary = empWithOT.calculateSalary();
            // String benefits = empWithOT.getBenefits();
            // txtPayrollReport.setText("New Salary: $" + newSalary + "\n" + benefits);

            lblStatus.setText("✓ Overtime applied using Decorator Pattern!");
            lblStatus.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    // ==================== EYAD HESHAM - DECORATOR PATTERN ====================

    /**
     * Apply certification decorator (Eyad Hesham)
     */
    @FXML
    private void handleApplyCertification() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // TODO: EYAD HESHAM - Use CertificationDecorator
            // Employee empWithCert = new CertificationDecorator(selected, "AWS Certified", 500.0);
            // double newSalary = empWithCert.calculateSalary();
            // String benefits = empWithCert.getBenefits();
            // txtPayrollReport.setText("New Salary: $" + newSalary + "\n" + benefits);

            lblStatus.setText("✓ Certification applied using Decorator Pattern!");
            lblStatus.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    // ==================== ALI HASSAN ALI - CRUD OPERATIONS ====================

    /**
     * Update selected employee (Ali Hassan Ali)
     */
    @FXML
    private void handleUpdateEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            selected.setFirstName(txtFirstName.getText());
            selected.setLastName(txtLastName.getText());
            selected.setEmail(txtEmail.getText());
            selected.setPhoneNumber(txtPhone.getText());
            selected.setDepartment(txtDepartment.getText());
            selected.setBaseSalary(Double.parseDouble(txtSalary.getText()));

            if (employeeDAO.updateEmployee(selected)) {
                loadEmployees();
                lblStatus.setText("✓ Employee updated!");
                lblStatus.setStyle("-fx-text-fill: green;");
            } else {
                lblStatus.setText("✗ Update failed.");
                lblStatus.setStyle("-fx-text-fill: red;");
            }

        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Delete selected employee (Ali Hassan Ali)
     */
    @FXML
    private void handleDeleteEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Delete " + selected.getFirstName() + " " + selected.getLastName() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            if (employeeDAO.deleteEmployee(selected.getEmployeeId())) {
                loadEmployees();
                clearFields();
                lblStatus.setText("✓ Employee deleted!");
                lblStatus.setStyle("-fx-text-fill: green;");
            } else {
                lblStatus.setText("✗ Delete failed.");
                lblStatus.setStyle("-fx-text-fill: red;");
            }
        }
    }

    /**
     * Search employees (Ali Hassan Ali)
     */
    @FXML
    private void handleSearch() {
        String keyword = txtSearch.getText();

        if (keyword.isEmpty()) {
            loadEmployees();
        } else {
            employeeList = FXCollections.observableArrayList(
                    employeeDAO.searchEmployees(keyword)
            );
            employeeTable.setItems(employeeList);
        }
    }

    /**
     * Clear input fields (Ali Hassan Ali)
     */
    @FXML
    private void clearFields() {
        txtEmployeeId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPhone.clear();
        dateHire.setValue(LocalDate.now());
        txtDepartment.clear();
        txtSalary.clear();
        cmbEmployeeType.setValue("Full-time");
        employeeTable.getSelectionModel().clearSelection();
    }
}