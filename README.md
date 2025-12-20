# Employee Management System - Project Documentation

## Project Overview

A comprehensive JavaFX-based Employee Management System demonstrating the implementation of multiple software design patterns. The system manages employee records, attendance, payroll, and related operations with a focus on scalability, maintainability, and clean architecture.

---

## Team Members

| Name | ID |
|------|---------|
| Omar Tamer | 2201585 |
| Ali Hassan | 2201576 |
| Shahd Amr | 2202148 |
| Abdelrahman Magdy | 2201605 |
| Fatma Mohamed | 2201744 |
| Eyad Hesham | 2202178 |

---

## Technology Stack

- **Language**: Java 15+
- **UI Framework**: JavaFX 21.0.6
- **Database**: MySQL 8.1.0
- **Build Tool**: Maven
- **Additional Libraries**: 
  - ControlsFX 11.2.1
  - FormsFX 11.6.0
  - ValidatorFX 0.6.1

---

## Design Patterns Implemented

### 1. **Singleton Pattern** (Abdelrahman Mohamed)
**Implementation**: `DatabaseConnection.java` and `PayrollSystem.java`

**Purpose**: Ensures only one instance exists throughout the application lifecycle.

**Key Features**:
- **DatabaseConnection**: Single database connection with automatic reconnection
- **PayrollSystem**: Centralized payroll processing with historical tracking
- Thread-safe implementation using synchronized methods
- Automatic database and table initialization

**Usage Example**:
```java
// Get database connection
DatabaseConnection.getInstance().getConnection();

// Process payroll
PayrollSystem.getInstance().processPayroll(employee);
```

---

### 2. **Factory Pattern** (Omar Tamer)
**Implementation**: `EmployeeFactory.java` and `DepartmentFactory.java`

**Purpose**: Creates objects without specifying exact class types, promoting loose coupling.

**Key Features**:
- **EmployeeFactory**: Creates Full-time, Part-time, or Contractor employees
- **DepartmentFactory**: Creates predefined department types (HR, IT, Finance, Sales, Operations)
- Type-specific parameter handling
- Validation and error handling

**Usage Example**:
```java
// Create employee
Employee emp = EmployeeFactory.createEmployee(
    "Full-time", id, firstName, lastName, 
    email, phone, hireDate, department, salary, 20, null
);

// Create department
Department dept = DepartmentFactory.createDepartment("IT", id);
```

---

### 3. **Builder Pattern** (Fatma Mohamed)
**Implementation**: `EmployeeBuilder.java`

**Purpose**: Constructs complex objects step-by-step, avoiding telescoping constructors.

**Key Features**:
- Fluent API for readable code
- Optional parameters with sensible defaults
- Supports all employee types
- Flexible construction process

**Usage Example**:
```java
Employee emp = new EmployeeBuilder(id, "John", "Doe")
    .email("john.doe@company.com")
    .phoneNumber("123-456-7890")
    .department(department)
    .baseSalary(5000.0)
    .employeeType("Full-time")
    .build();
```

---

### 4. **Prototype Pattern** (Shahd Amr)
**Implementation**: `EmployeePrototypeRegistry.java`

**Purpose**: Creates new objects by cloning existing prototypes, improving performance.

**Key Features**:
- Registry-based prototype management
- Pre-initialized prototypes for each employee type
- Cloning with customization support
- Type normalization for robust lookup

**Usage Example**:
```java
// Initialize prototypes
EmployeePrototypeRegistry.initializePrototypes();

// Clone from prototype
Employee clone = EmployeePrototypeRegistry.getPrototype("fulltime");
clone.setEmployeeId(newId);
clone.setFirstName("New Name");
```

---

### 5. **Decorator Pattern** (Abdelrahman Magdy & Eyad Hesham)
**Implementation**: `EmployeeDecorator.java`, `BonusDecorator.java`, `OvertimeDecorator.java`, `CertificationDecorator.java`

**Purpose**: Dynamically adds responsibilities to objects without modifying original classes.

**Key Features**:

**BonusDecorator** (Abdelrahman Magdy):
- Adds performance bonuses
- Tracks bonus amount and reason
- Updates salary calculation

**OvertimeDecorator** (Abdelrahman Magdy):
- Calculates overtime pay
- Tracks hours and hourly rate
- Adds overtime compensation

**CertificationDecorator** (Eyad Hesham):
- Adds professional certification benefits
- Provides certification allowances
- Enhances employee benefits string

**Usage Example**:
```java
// Apply bonus
Employee empWithBonus = new BonusDecorator(employee, 1000.0, "Performance");
double newSalary = empWithBonus.calculateSalary();

// Apply overtime
Employee empWithOT = new OvertimeDecorator(employee, 10, 25.0);

// Apply certification
Employee empWithCert = new CertificationDecorator(employee, "AWS Certified", 500.0);
```

---

## System Architecture

### Database Schema

**Departments Table**:
```sql
CREATE TABLE departments (
    department_id INT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL,
    manager_name VARCHAR(100),
    location VARCHAR(150)
);
```

**Employees Table**:
```sql
CREATE TABLE employees (
    employee_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(20),
    hire_date DATE NOT NULL,
    department_id INT,
    base_salary DECIMAL(10,2) NOT NULL,
    employee_type VARCHAR(20) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(department_id)
);
```

**Payroll Records Table**:
```sql
CREATE TABLE payroll_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    payment_date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_type VARCHAR(50),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
```

---

## Employee Types

### 1. **Full-Time Employee**
- Fixed monthly salary
- Benefits: Health insurance, pension, annual leave (20 days)
- Standard employment contract

### 2. **Part-Time Employee**
- Hourly-based compensation
- Salary calculated: `hourlyRate × hoursPerWeek × 4`
- Benefits: Flexible schedule, limited leave

### 3. **Contractor**
- Project-based payment
- Contract end date tracking
- Benefits: Project-specific terms

---

## Key Features

### Employee Management
- ✅ Add employees using Factory or Builder patterns
- ✅ Update employee information
- ✅ Delete employees with confirmation
- ✅ Search by name or department
- ✅ Clone employees from prototypes

### Department Management
- ✅ Create departments using Factory pattern
- ✅ Predefined department configurations
- ✅ Department-employee relationship management

### Payroll Processing
- ✅ Centralized payroll system (Singleton)
- ✅ Process individual employee payments
- ✅ Generate comprehensive payroll reports
- ✅ Track payment history
- ✅ Prevent duplicate daily payments

### Compensation Decorators
- ✅ Apply performance bonuses
- ✅ Calculate overtime pay
- ✅ Add certification allowances
- ✅ Automatically update database salaries

---

## GUI Components (Ali Hassan Ali)

### Main Interface
- **Left Panel**: Employee input form and action buttons
- **Right Panel**: Employee table view and payroll reports
- **Top Bar**: Application title
- **Bottom Bar**: Footer information

### Input Fields
- First Name, Last Name
- Email, Phone Number
- Hire Date (DatePicker)
- Department (ComboBox)
- Base Salary
- Employee Type (ComboBox)

### Action Buttons
- **Add (Factory)**: Create employee using Factory pattern
- **Add (Builder)**: Create employee using Builder pattern
- **Update**: Modify selected employee
- **Delete**: Remove employee with confirmation
- **Clear**: Reset all input fields
- **Clone (Prototype)**: Duplicate selected employee
- **Process Payroll**: Calculate and record payment
- **Apply Decorators**: Add bonus, overtime, or certification
- **Create Department**: Generate new department
- **Generate Report**: Display payroll summary

---

## Database Configuration

**Connection Details** (Configured in `DatabaseConnection.java`):
```java
private static final String DB_NAME = "employee_management_db";
private static final String URL = "jdbc:mysql://localhost:3306/";
private static final String USERNAME = "root";
private static final String PASSWORD = "khemu123456";
```

### Automatic Initialization
The system automatically:
1. Creates the database if missing
2. Creates all required tables
3. Inserts sample data for testing
4. Establishes persistent connection with auto-reconnect

---

## Data Access Layer

### EmployeeDAO
**Methods**:
- `insertEmployee(Employee)` - Add new employee
- `getAllEmployees()` - Retrieve all employees with department info
- `getEmployeeById(int)` - Get specific employee
- `updateEmployee(Employee)` - Modify employee data
- `deleteEmployee(int)` - Remove employee
- `searchEmployees(String)` - Search by keyword
- `updateSalary(int, double)` - Update salary after decorators

### DepartmentDAO
**Methods**:
- `insertDepartment(Department)` - Add new department
- `getAllDepartments()` - Retrieve all departments
- `getDepartmentById(int)` - Get specific department
- `getDepartmentByNameIgnoreCase(String)` - Case-insensitive lookup
- `updateDepartment(Department)` - Modify department
- `deleteDepartment(int)` - Remove department

---

## Error Handling

### Input Validation
- Required field checks (first name, last name)
- Department selection validation
- Numeric format validation for salary
- Duplicate department prevention
- Certification tracking (prevents double application)

### Database Operations
- Connection validation and auto-reconnection
- SQL exception handling with user-friendly messages
- Transaction rollback support
- Graceful degradation on errors

### User Feedback
- Status label updates for all operations
- Color-coded messages (green for success, red for errors)
- Confirmation dialogs for destructive actions
- Detailed error messages in console logs

---

## Running the Application

### Prerequisites
1. **Java Development Kit (JDK) 15+**
2. **MySQL Server 8.0+** running on localhost:3306
3. **Maven** for dependency management

### Setup Steps

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd Employee-Management-System
   ```

2. **Configure Database**
   - Ensure MySQL is running
   - Update credentials in `DatabaseConnection.java` if needed
   - Database will be created automatically on first run

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn javafx:run
   ```
   Or run `Launcher.java` from your IDE

### Alternative Launch Methods
- **Command Line**: `java -jar target/employee-management-system-1.0-SNAPSHOT.jar`
- **IDE**: Run `MainApplication.java` or `Launcher.java`

---

## Project Structure

```
Employee-Management-System/
├── src/main/java/com/employeemanagementsystem/
│   ├── MainApplication.java          # JavaFX entry point
│   ├── Launcher.java                 # Alternative launcher
│   ├── controller/
│   │   └── MainController.java       # UI controller (Ali Hassan)
│   ├── model/
│   │   ├── Employee.java             # Abstract base class
│   │   ├── FullTimeEmployee.java    # Full-time implementation
│   │   ├── PartTimeEmployee.java    # Part-time implementation
│   │   ├── Contractor.java          # Contractor implementation
│   │   └── Department.java          # Department entity
│   ├── database/
│   │   ├── EmployeeDAO.java         # Employee data access
│   │   └── DepartmentDAO.java       # Department data access
│   └── patterns/
│       ├── singleton/
│       │   ├── DatabaseConnection.java    # DB Singleton (Ali Hassan)
│       │   └── PayrollSystem.java         # Payroll Singleton (Abdelrahman Mohamed)
│       ├── factory/
│       │   ├── EmployeeFactory.java       # Employee Factory (Omar Tamer)
│       │   └── DepartmentFactory.java     # Department Factory (Omar Tamer)
│       ├── builder/
│       │   └── EmployeeBuilder.java       # Builder (Fatma Mohamed)
│       ├── prototype/
│       │   └── EmployeePrototypeRegistry.java  # Prototype (Shahd Amr)
│       └── decorator/
│           ├── EmployeeDecorator.java     # Base decorator
│           ├── BonusDecorator.java        # Bonus (Abdelrahman Magdy)
│           ├── OvertimeDecorator.java     # Overtime (Abdelrahman Magdy)
│           └── CertificationDecorator.java # Certification (Eyad Hesham)
├── src/main/resources/com/employeemanagementsystem/
│   └── MainView.fxml                 # UI layout
├── pom.xml                           # Maven configuration
└── README.md                         # Project documentation
```

---

## Sample Data

The system includes pre-loaded sample data:

**Departments**:
- Human Resources (ID: 1)
- Finance (ID: 2)

**Employees**:
1. John Doe - Full-time, HR, $6,000
2. Jane Smith - Full-time, HR, $5,500
3. Bob Johnson - Full-time, HR, $5,800
4. Alice Williams - Part-time, Finance, $3,200
5. Charlie Brown - Contractor, Finance, $7,000

**Payroll Records**: Initial payments for all sample employees

---

## Design Pattern Benefits

| Pattern | Benefit |
|---------|---------|
| **Singleton** | Prevents resource conflicts, ensures data consistency |
| **Factory** | Simplifies object creation, promotes loose coupling |
| **Builder** | Improves readability, handles complex construction |
| **Prototype** | Improves performance, reduces initialization overhead |
| **Decorator** | Adds flexibility, avoids class explosion |

---

## Future Enhancements

### Potential Improvements
1. **Authentication System** - User login and role-based access
2. **Attendance Tracking** - Clock in/out functionality
3. **Leave Management** - Request and approve time off
4. **Performance Reviews** - Employee evaluation system
5. **Reporting Dashboard** - Charts and analytics
6. **Email Notifications** - Automated alerts
7. **Document Management** - File attachments
8. **Audit Trail** - Change history tracking

### Additional Patterns
- **Observer Pattern** - Real-time notifications
- **Strategy Pattern** - Flexible payroll calculation methods
- **Command Pattern** - Undo/redo operations
- **Facade Pattern** - Simplified API for complex subsystems

---

## Testing

### Manual Testing Checklist
- ✅ Add employee using Factory pattern
- ✅ Add employee using Builder pattern
- ✅ Update employee information
- ✅ Delete employee
- ✅ Search employees
- ✅ Clone employee from prototype
- ✅ Create department
- ✅ Process payroll
- ✅ Apply bonus decorator
- ✅ Apply overtime decorator
- ✅ Apply certification decorator
- ✅ Generate payroll report

### Database Testing
- ✅ Automatic database creation
- ✅ Table initialization
- ✅ Sample data insertion
- ✅ Foreign key constraints
- ✅ Connection persistence

---

## Known Limitations

1. **Single User**: No multi-user support or concurrency control
2. **Local Database**: Requires local MySQL installation
3. **Hard-coded Credentials**: Database credentials in source code
4. **No Encryption**: Passwords and sensitive data unencrypted
5. **Limited Validation**: Basic input validation only
6. **No Backup**: Manual database backup required

---

## Troubleshooting

### Common Issues

**1. Database Connection Failed**
- Verify MySQL is running on localhost:3306
- Check username/password in `DatabaseConnection.java`
- Ensure MySQL connector dependency is resolved

**2. JavaFX Runtime Error**
- Confirm JDK 15+ is installed
- Verify JavaFX libraries are on classpath
- Run via Maven: `mvn javafx:run`

**3. Prototype Clone Not Working**
- Ensure prototypes are initialized on startup
- Check type normalization (no spaces/hyphens)
- Verify prototype key matches registry

**4. Decorator Not Updating Salary**
- Confirm `updateSalary()` is called after decoration
- Check database connection is active
- Verify employee ID exists in database

---

## Conclusion

This Employee Management System demonstrates practical application of five essential design patterns in a real-world scenario. The system provides a solid foundation for understanding:

- **Creational Patterns**: Singleton, Factory, Builder, Prototype
- **Structural Patterns**: Decorator
- **Database Integration**: JDBC, DAO pattern
- **UI Development**: JavaFX with FXML
- **Software Architecture**: Clean, maintainable, scalable design

The project successfully showcases how design patterns solve common software engineering challenges while maintaining code quality and flexibility.

---

## License

© 2024 Employee Management System - University Project

---

## Contact

For questions or issues, please contact any team member:
- Omar Tamer (2201585)
- Ali Hassan (2201576)
- Shahd Amr (2202148)
- Abdelrahman Magdy (2201605)
- Fatma Mohamed (2201744)
- Eyad Hesham (2202178)
