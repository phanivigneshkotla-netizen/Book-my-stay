/**
 * Hotel Booking Management System - Use Case 1: Application Entry & Welcome Message
 * 
 * This class serves as the entry point for the Hotel Booking Management System.
 * It demonstrates the basic structure of a Java application with a welcome message
 * and application version information.
 * 
 * Key Concepts:
 * - Class: Container for application behavior and logical boundary
 * - main() Method: Entry point for standalone Java applications
 * - static Keyword: Allows method execution without object instantiation
 * - Console Output: System.out.println() for displaying output
 * - String Literals: Immutable text enclosed in double quotes
 * - Method Invocation: Calling methods on objects
 * - Linear Execution: Top-to-bottom flow in main() method
 * 
 * @author Hotel Booking Team
 * @version 1.0
 * @since 2026-04-01
 */
public class UseCase1HotelBookingApp {
    
    /**
     * Main method - Entry point of the application.
     * This is the method the JVM looks for when executing the program.
     * 
     * The method signature must be exactly:
     * public static void main(String[] args)
     * 
     * @param args Command-line arguments passed to the application (not used in this use case)
     */
    public static void main(String[] args) {
        // Print welcome message
        System.out.println("========================================");
        System.out.println("Welcome to Hotel Booking Management System");
        System.out.println("Application Name: Hotel Booking System");
        System.out.println("Version: 1.0");
        System.out.println("========================================");
        System.out.println("Application started successfully!");
    }
}
