/**
 * Use Case 2: Basic Room Types & Static Availability
 * Application Entry Point for Room Initialization and Display
 * 
 * This class demonstrates the creation and initialization of different room types
 * using object-oriented design principles including inheritance and polymorphism.
 * Room availability is stored using simple variables, which highlights the limitations
 * of hardcoded state management that will be addressed in future use cases.
 * 
 * Key Concepts:
 * - Abstract Class & Inheritance: Different room types inherit from the Room class
 * - Polymorphism: Room objects are handled using the Room reference type
 * - Encapsulation: Room details are encapsulated within room objects
 * - Static Availability: Availability stored using individual variables
 * - Separation of Domain and State: Room objects vs. availability variables
 * 
 * Flow:
 * 1. Room objects are created representing different room types
 * 2. Availability information is stored using simple variables
 * 3. Room details and availability are displayed to the console
 * 4. Application terminates
 * 
 * Note: This use case intentionally uses simple variables for availability to demonstrate
 *       the limitations of this approach before introducing data structures in later use cases.
 * 
 * @author Hotel Booking Team
 * @version 2.1
 * @since 2026-04-01
 */
public class UseCase2RoomInitialization {
    
    // Static availability variables - Simple representation (Limitation: hardcoded and scattered)
    // These will be refactored into a data structure in future use cases
    static int singleRoomAvailability = 10;
    static int doubleRoomAvailability = 8;
    static int suiteRoomAvailability = 3;
    
    /**
     * Main method - Entry point of the application
     * Initializes room objects and displays their details along with availability
     * 
     * @param args Command-line arguments (not used in this use case)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 2: Basic Room Types & Static Availability");
        System.out.println("Version 2.1");
        System.out.println("========================================\n");
        
        // Create Room objects using polymorphism
        // All room objects are referenced using the Room type
        Room singleRoom = new SingleRoom("Queen");
        Room doubleRoom = new DoubleRoom("City View");
        Room suiteRoom = new SuiteRoom(2);
        
        // Display Single Room Information
        System.out.println("--- SINGLE ROOM ---");
        singleRoom.displayRoomInfo();
        System.out.println("Available Rooms: " + singleRoomAvailability);
        System.out.println();
        
        // Display Double Room Information
        System.out.println("--- DOUBLE ROOM ---");
        doubleRoom.displayRoomInfo();
        System.out.println("Available Rooms: " + doubleRoomAvailability);
        System.out.println();
        
        // Display Suite Room Information
        System.out.println("--- SUITE ROOM ---");
        suiteRoom.displayRoomInfo();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
        System.out.println();
        
        // Display Summary
        System.out.println("========================================");
        System.out.println("AVAILABILITY SUMMARY");
        System.out.println("========================================");
        System.out.println("Total Single Rooms Available: " + singleRoomAvailability);
        System.out.println("Total Double Rooms Available: " + doubleRoomAvailability);
        System.out.println("Total Suite Rooms Available: " + suiteRoomAvailability);
        System.out.println("Total Available Rooms: " + 
                          (singleRoomAvailability + doubleRoomAvailability + suiteRoomAvailability));
        System.out.println("========================================");
        System.out.println("Application terminated successfully!");
    }
}
