package com.employeemanagementsystem.patterns.prototype;

import com.employeemanagementsystem.model.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * PROTOTYPE PATTERN - Employee Prototype Registry
 * BY: SHAHD AMR
 */
public class EmployeePrototypeRegistry {
    private static Map<String, Employee> prototypes = new HashMap<>();

    /**
     * Initialize prototypes - called once when app starts
     */
    public static void initializePrototypes() {
        Department generalDept = new Department(0, "General", "TBD", "TBD");

        // Create Full-time prototype
        FullTimeEmployee fullTime = new FullTimeEmployee(
                0, "Prototype", "FullTime", "prototype@company.com", "000-000-0000",
                LocalDate.now(), generalDept, 5000.0, 20
        );

        // Create Part-time prototype
        PartTimeEmployee partTime = new PartTimeEmployee(
                0, "Prototype", "PartTime", "prototype@company.com", "000-000-0000",
                LocalDate.now(), generalDept, 3000.0, 20, 15.0
        );

        // Create Contractor prototype
        Contractor contractor = new Contractor(
                0, "Prototype", "Contractor", "prototype@company.com", "000-000-0000",
                LocalDate.now(), generalDept, 7000.0, LocalDate.now().plusYears(1), "General Project"
        );

        // Add to registry
        prototypes.put("fulltime", fullTime);
        prototypes.put("parttime", partTime);
        prototypes.put("contractor", contractor);

        System.out.println("✓ Prototype Registry initialized!");
    }

    /**
     * Add a prototype to registry
     */
    public static void addPrototype(String key, Employee prototype) {
        prototypes.put(key, prototype);
    }

    /**
     * Get a cloned employee from prototype
     */
    public static Employee getPrototype(String key) {
        Employee prototype = prototypes.get(key);
        if (prototype == null) {
            return null;
        }
        return prototype.clone();
    }

    /**
     * Clone and customize with new values
     */
    public static Employee cloneAndCustomize(String prototypeKey, int newId,
                                             String firstName, String lastName,
                                             String email, String department) {
        // Get clone
        Employee clone = getPrototype(prototypeKey);
        if (clone == null) {
            return null;
        }

        // Customize
        clone.setEmployeeId(newId);
        clone.setFirstName(firstName);
        clone.setLastName(lastName);
        clone.setEmail(email);

        Department dept = new Department(0, department, "Manager", "Location");
        clone.setDepartment(dept);

        return clone;
    }

    /**
     * Check if prototype exists
     */
    public static boolean hasPrototype(String key) {
        return prototypes.containsKey(key);
    }

    /**
     * Get all prototype keys
     */
    public static String[] getAvailablePrototypes() {
        return prototypes.keySet().toArray(new String[0]);
    }

    /**
     * Remove a prototype
     */
    public static void removePrototype(String key) {
        prototypes.remove(key);
    }

    /**
     * Clear all prototypes
     */
    public static void clearPrototypes() {
        prototypes.clear();
        System.out.println("All prototypes cleared.");
    }
}