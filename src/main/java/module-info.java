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

    // Export main package for other modules (if needed)
    exports com.employeemanagementsystem;
}
