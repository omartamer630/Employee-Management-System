module com.example.employeemangementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    // Open packages to javafx.fxml for reflection
    opens com.employeemanagementsystem to javafx.fxml;
    opens com.employeemanagementsystem.controller to javafx.fxml;

    // CRITICAL: Add this line to open your model package to JavaFX base
    opens com.employeemanagementsystem.model to javafx.base, javafx.fxml;

    // Export main package for other modules (if needed)
    exports com.employeemanagementsystem;
    exports com.employeemanagementsystem.model; // Also export the model if needed
}