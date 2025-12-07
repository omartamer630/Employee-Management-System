package com.employeemanagementsystem.patterns.prototype;

import com.employeemanagementsystem.model.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * PROTOTYPE PATTERN - Employee Prototype Registry
 * Maintains a registry of prototype employee objects that can be cloned
 * This is useful when creating similar objects is expensive or complex
 * Instead of creating from scratch, we clone existing prototypes and modify them
 */
public class EmployeePrototypeRegistry {
    // Store prototype employees
    private static Map<String, Employee> prototypes = new HashMap<>();

    /**
     * Initialize the registry with common employee prototypes
     * Call this once when the application starts
     */
    public static void initializePrototypes() {
        // TODO: Create prototype employees and add them to the registry
        // Create sample employees for each type that can be cloned later
        //
        // Example structure:
        // 1. Create a FullTimeEmployee prototype with default values
        //    - Use generic values: id=0, name="Prototype", department="General", salary=5000
        // 2. Create a PartTimeEmployee prototype
        // 3. Create a Contractor prototype
        // 4. Add each to the prototypes map with keys like "fulltime", "parttime", "contractor"

    }

    /**
     * Add a prototype employee to the registry
     */
    public static void addPrototype(String key, Employee prototype) {
        // TODO: Implement method to add a prototype
        // Simply put the prototype in the map with the given key

    }

    /**
     * Get a cloned employee from a prototype
     * This clones the prototype and returns a new object with the same properties
     */
    public static Employee getPrototype(String key) {
        // TODO: Implement prototype cloning logic
        // 1. Get the prototype from the map using the key
        // 2. If prototype exists, clone it using the clone() method
        // 3. Return the cloned employee
        // 4. If prototype doesn't exist, return null or throw exception

        return null;
    }

    /**
     * Clone a prototype and customize it with new values
     * This is the main advantage of the prototype pattern - easy customization
     */
    public static Employee cloneAndCustomize(String prototypeKey, int newId,
                                             String firstName, String lastName,
                                             String email, String department) {
        // TODO: Implement clone and customize logic
        // 1. Get a clone of the prototype using getPrototype method
        // 2. If clone is not null, set the new values:
        //    - Set employee ID to newId
        //    - Set first name to firstName
        //    - Set last name to lastName
        //    - Set email to email
        //    - Set department to department
        // 3. Return the customized clone
        // 4. If prototype doesn't exist, return null

        return null;
    }

    /**
     * Check if a prototype exists in the registry
     */
    public static boolean hasPrototype(String key) {
        // TODO: Check if prototype exists in map
        // Return true if key exists in prototypes map, false otherwise

        return false;
    }

    /**
     * Get all available prototype keys
     */
    public static String[] getAvailablePrototypes() {
        // TODO: Return array of all prototype keys
        // Convert the keySet of prototypes map to an array and return it

        return new String[0];
    }

    /**
     * Remove a prototype from the registry
     */
    public static void removePrototype(String key) {
        // TODO: Remove prototype from map
        // Remove the entry with the given key from prototypes map

    }

    /**
     * Clear all prototypes (useful for testing or reset)
     */
    public static void clearPrototypes() {
        prototypes.clear();
        System.out.println("All prototypes cleared.");
    }
}