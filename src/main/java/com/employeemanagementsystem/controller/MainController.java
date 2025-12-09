package com.employeemanagementsystem.controller;

import com.employeemanagementsystem.database.*;
import com.employeemanagementsystem.model.*;
import com.employeemanagementsystem.patterns.builder.EmployeeBuilder;
import com.employeemanagementsystem.patterns.decorator.*;
import com.employeemanagementsystem.patterns.factory.*;
import com.employeemanagementsystem.patterns.prototype.EmployeePrototypeRegistry;
import com.employeemanagementsystem.patterns.singleton.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Main Controller for the Employee Management System (Ali Hassan Ali)
 * Integrates all design patterns and manages the GUI
 *
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

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private DatePicker dateHire;
    @FXML private ComboBox<Department> cmbDepartment; // Changed from TextField to ComboBox
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
        colDepartment.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDepartmentName())
        );
        colType.setCellValueFactory(new PropertyValueFactory<>("employeeType"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));

        cmbEmployeeType.setItems(FXCollections.observableArrayList(
                "Full-time", "Part-time", "Contractor"
        ));
        cmbEmployeeType.setValue("Full-time");

        // Load departments into ComboBox
        loadDepartments();

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
     * Load departments into ComboBox
     */
    private void loadDepartments() {
        List<Department> departments = departmentDAO.getAllDepartments();
        cmbDepartment.setItems(FXCollections.observableArrayList(departments));
        cmbDepartment.setCellFactory(param -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);
                if (empty || department == null) {
                    setText(null);
                } else {
                    setText(department.getDepartmentName());
                }
            }
        });
        cmbDepartment.setConverter(new StringConverter<Department>() {
            @Override
            public String toString(Department department) {
                return department != null ? department.getDepartmentName() : "";
            }

            @Override
            public Department fromString(String string) {
                return null; // Not needed for display only
            }
        });

        // Set prompt text
        cmbDepartment.setPromptText("Select Department");
    }

    /**
     * Display selected employee details (Ali Hassan Ali)
     */
    private void displayEmployeeDetails(Employee employee) {
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtEmail.setText(employee.getEmail());
        txtPhone.setText(employee.getPhoneNumber());
        dateHire.setValue(employee.getHireDate());
        cmbDepartment.setValue(employee.getDepartment()); // Updated to use ComboBox
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
            // Get input values from the form
            int id = (int)(Math.random() * 10000) + 1;
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            LocalDate hireDate = dateHire.getValue();
            Department department = cmbDepartment.getValue(); // Get selected Department object
            double salary = Double.parseDouble(txtSalary.getText());
            String type = cmbEmployeeType.getValue();

            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty()) {
                lblStatus.setText("✗ First name and last name are required.");
                lblStatus.setStyle("-fx-text-fill: red;");
                return;
            }

            if (department == null) {
                lblStatus.setText("✗ Please select a department.");
                lblStatus.setStyle("-fx-text-fill: red;");
                return;
            }

            if (hireDate == null) {
                hireDate = LocalDate.now();
            }

            // Use EmployeeFactory to create the appropriate employee type
            Employee newEmployee = null;

            if (type.equalsIgnoreCase("Full-time")) {
                // For Full-time: additionalParam1 = 20 (annual leave days)
                newEmployee = EmployeeFactory.createEmployee(
                        type, id, firstName, lastName, email, phone,
                        hireDate, department, salary, // Pass Department object
                        20, null  // 20 annual leave days
                );
            }
            else if (type.equalsIgnoreCase("Part-time")) {
                // For Part-time: additionalParam1 = 20 (hours/week), additionalParam2 = 15.0 (hourly rate)
                newEmployee = EmployeeFactory.createEmployee(
                        type, id, firstName, lastName, email, phone,
                        hireDate, department, salary, // Pass Department object
                        20, 15.0  // 20 hours per week, $15 per hour
                );
            }
            else if (type.equalsIgnoreCase("Contractor")) {
                // For Contractor: additionalParam1 = LocalDate (end date), additionalParam2 = "Project Name"
                LocalDate contractEndDate = LocalDate.now().plusYears(1);
                newEmployee = EmployeeFactory.createEmployee(
                        type, id, firstName, lastName, email, phone,
                        hireDate, department, salary, // Pass Department object
                        contractEndDate, "General Project"
                );
            }

            // Insert employee into database
            if (newEmployee != null && employeeDAO.insertEmployee(newEmployee)) {
                loadEmployees();
                clearFields();
                lblStatus.setText("✓ Employee added using Factory Pattern! Type: " + type);
                lblStatus.setStyle("-fx-text-fill: green;");
            } else {
                lblStatus.setText("✗ Failed to add employee.");
                lblStatus.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            lblStatus.setText("✗ Error: Invalid number format. Please check ID and Salary.");
            lblStatus.setStyle("-fx-text-fill: red;");
        } catch (IllegalArgumentException e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    /**
     * Create department using DepartmentFactory (Omar Tamer)
     */
    @FXML
    private void handleCreateDepartment() {
        TextInputDialog dialog = new TextInputDialog("IT");
        dialog.setTitle("Create Department");
        dialog.setHeaderText("Create New Department Using Factory Pattern");
        dialog.setContentText("Enter department type:");

        // Add helpful information
        Label helpLabel = new Label(
                "Valid types:\n• HR\n• IT\n• Finance\n• Sales\n• Operations"
        );
        dialog.getDialogPane().setExpandableContent(helpLabel);

        dialog.showAndWait().ifPresent(type -> {
            try {
                // Check if department already exists (case-insensitive)
                Optional<Department> existing = departmentDAO.getDepartmentByNameIgnoreCase(type);
                if (existing.isPresent()) {
                    // Show error inside the dialog as an alert
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Department Already Exists");
                    errorAlert.setHeaderText("Cannot create department");
                    errorAlert.setContentText("A department named '" + type + "' already exists.");
                    errorAlert.showAndWait();
                    return; // Stop further processing
                }

                // Generate random ID
                int id = (int)(Math.random() * 1000) + 1;

                // Create department via factory
                Department dept = DepartmentFactory.createDepartment(type, id);

                // Insert department
                if (departmentDAO.insertDepartment(dept)) {
                    lblStatus.setText(
                            "✓ Department created using Factory Pattern!\n" +
                                    "Name: " + dept.getDepartmentName() + "\n" +
                                    "Manager: " + dept.getManagerName() + "\n" +
                                    "Location: " + dept.getLocation()
                    );
                    lblStatus.setStyle("-fx-text-fill: green;");

                    // Reload departments in ComboBox
                    loadDepartments();

                    // Show success alert
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Department Created");
                    successAlert.setHeaderText("Department Created Successfully!");
                    successAlert.setContentText(
                            "Department: " + dept.getDepartmentName() + "\n" +
                                    "ID: " + dept.getDepartmentId() + "\n" +
                                    "Manager: " + dept.getManagerName() + "\n" +
                                    "Location: " + dept.getLocation()
                    );
                    successAlert.showAndWait();
                } else {
                    lblStatus.setText("✗ Failed to create department in database.");
                    lblStatus.setStyle("-fx-text-fill: red;");
                }

            } catch (IllegalArgumentException e) {
                lblStatus.setText("✗ Error: " + e.getMessage());
                lblStatus.setStyle("-fx-text-fill: red;");
            } catch (Exception e) {
                lblStatus.setText("✗ Error creating department: " + e.getMessage());
                lblStatus.setStyle("-fx-text-fill: red;");
                e.printStackTrace();
            }
        });
    }

    // ==================== FATMA MOHAMED - BUILDER PATTERN ====================

    /**
     * Add employee using EmployeeBuilder (Fatma Mohamed)
     */
    @FXML
    private void handleAddEmployeeWithBuilder() {
        try {
            int id = (int)(Math.random() * 10000) + 1;
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();

            // TODO: FATMA MOHAMED - Use EmployeeBuilder with method chaining
            // Note: You'll need to update EmployeeBuilder to accept Department object
            // Employee emp = new EmployeeBuilder(id, firstName, lastName)
            //     .email(txtEmail.getText())
            //     .phoneNumber(txtPhone.getText())
            //     .hireDate(dateHire.getValue())
            //     .department(cmbDepartment.getValue()) // Updated
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
     * Clone employee using Prototype Pattern
     * BY: SHAHD AMR
     *
     * Replace the handleCloneEmployee method in MainController.java with this code:
     */
    @FXML
    private void handleCloneEmployee() {
        // Check if employee is selected
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblStatus.setText("✗ Please select an employee to clone.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Step 1: Clone the selected employee
            Employee clone = selected.clone();

            // Step 2: Give it a new ID
            int newId = (int)(Math.random() * 10000) + 1000;
            clone.setEmployeeId(newId);

            // Step 3: Change the name so we know it's a copy
            clone.setFirstName("Copy of " + clone.getFirstName());
            clone.setEmail("copy." + clone.getEmail());

            // Step 4: Save to database
            if (employeeDAO.insertEmployee(clone)) {
                loadEmployees();
                displayEmployeeDetails(clone);

                lblStatus.setText("✓ Employee cloned! New ID: " + newId);
                lblStatus.setStyle("-fx-text-fill: green;");
            } else {
                lblStatus.setText("✗ Failed to save cloned employee.");
                lblStatus.setStyle("-fx-text-fill: red;");
            }

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
            System.out.println("selected is null");
            lblStatus.setText("✗ Please select an employee.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        PayrollSystem payroll = PayrollSystem.getInstance();
        payroll.processPayroll(selected);

        double total = payroll.getTotalPaid(selected.getEmployeeId());

        lblStatus.setText("✓ Payroll processed! Total Paid: $" + total);
        lblStatus.setStyle("-fx-text-fill: green;");
    }

    /**
     * Generate payroll report (Abdelrahman Mohamed)
     */
    @FXML
    private void handleGeneratePayrollReport() {
        System.out.println("generating report");
        PayrollSystem payroll = PayrollSystem.getInstance();
        String report = payroll.generatePayrollReport();

        txtPayrollReport.setText(report);
        lblStatus.setText("✓ Payroll Report Generated!");
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
            selected.setDepartment(cmbDepartment.getValue()); // Updated to use ComboBox
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
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPhone.clear();
        dateHire.setValue(LocalDate.now());
        cmbDepartment.getSelectionModel().clearSelection(); // Clear ComboBox selection
        txtSalary.clear();
        cmbEmployeeType.setValue("Full-time");
        employeeTable.getSelectionModel().clearSelection();
    }
}