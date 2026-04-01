/**
 * Use Case 3: Centralized Room Inventory Management
 * Application Entry Point for Inventory Setup and Demonstration
 * 
 * This class demonstrates how HashMap solves the problem of scattered availability
 * variables by providing a centralized, scalable inventory management system.
 * 
 * Key Concepts Demonstrated:
 * - HashMap: O(1) lookup and update operations for room availability
 * - Single Source of Truth: All availability data in one centralized structure
 * - Encapsulation: Inventory operations controlled through RoomInventory class
 * - Separation of Concerns: Inventory logic separated from room domain model
 * - Scalability: Easy to add new room types without changing system logic
 * 
 * Problem Addressed:
 * Use Case 2 used scattered static variables for availability:
 *   - static int singleRoomAvailability = 10;
 *   - static int doubleRoomAvailability = 8;
 *   - static int suiteRoomAvailability = 3;
 * 
 * Issues with scattered approach:
 *   - Difficult to manage as room types increase
 *   - Risk of inconsistent updates
 *   - No central control over availability operations
 *   - Poor scalability
 * 
 * Solution - Centralized HashMap:
 *   - Single RoomInventory instance manages all availability
 *   - O(1) access and update times
 *   - Easy to add/remove room types
 *   - Consistent state management
 *   - Better encapsulation and control
 * 
 * Flow:
 * 1. Initialize RoomInventory
 * 2. Register room types with their availability
 * 3. Demonstrate availability checks
 * 4. Demonstrate booking and cancellation
 * 5. Display final inventory state
 * 
 * @author Hotel Booking Team
 * @version 3.1
 * @since 2026-04-01
 */
public class UseCase3InventorySetup {
    
    /**
     * Main method - Entry point demonstrating centralized inventory management
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 3: Centralized Room Inventory Management");
        System.out.println("Version 3.1");
        System.out.println("========================================");
        
        // Step 1: Initialize the centralized inventory
        System.out.println("\n[STEP 1] Initializing Centralized Room Inventory...");
        RoomInventory inventory = new RoomInventory();
        System.out.println("✓ Room Inventory initialized (using HashMap for O(1) operations)");
        
        // Step 2: Register room types with their availability
        // This demonstrates the scalability advantage - adding new types is trivial
        System.out.println("\n[STEP 2] Registering Room Types in Inventory...");
        inventory.addRoom("Single Room", 10);
        System.out.println("✓ Single Room: 10 rooms registered");
        
        inventory.addRoom("Double Room", 8);
        System.out.println("✓ Double Room: 8 rooms registered");
        
        inventory.addRoom("Suite Room", 3);
        System.out.println("✓ Suite Room: 3 rooms registered");
        
        // Step 3: Display initial inventory state
        System.out.println("\n[STEP 3] Displaying Initial Inventory State...");
        inventory.displayInventory();
        
        // Step 4: Demonstrate availability checks (O(1) operation)
        System.out.println("[STEP 4] Checking Room Availability (O(1) HashMap lookup)...");
        System.out.println("Single Room Availability: " + inventory.getAvailability("Single Room") + " rooms");
        System.out.println("Double Room Availability: " + inventory.getAvailability("Double Room") + " rooms");
        System.out.println("Suite Room Availability: " + inventory.getAvailability("Suite Room") + " rooms");
        
        // Step 5: Demonstrate booking rooms
        System.out.println("\n[STEP 5] Processing Room Bookings...");
        boolean singleBooked = inventory.bookRoom("Single Room");
        System.out.println("✓ Single Room booked: " + (singleBooked ? "Success" : "Failed"));
        System.out.println("  Remaining Single Rooms: " + inventory.getAvailability("Single Room"));
        
        boolean doubleBooked = inventory.bookRoom("Double Room");
        System.out.println("✓ Double Room booked: " + (doubleBooked ? "Success" : "Failed"));
        System.out.println("  Remaining Double Rooms: " + inventory.getAvailability("Double Room"));
        
        boolean suiteBooked = inventory.bookRoom("Suite Room");
        System.out.println("✓ Suite Room booked: " + (suiteBooked ? "Success" : "Failed"));
        System.out.println("  Remaining Suite Rooms: " + inventory.getAvailability("Suite Room"));
        
        // Additional bookings
        System.out.println("\n✓ Booking 2 more Single Rooms...");
        inventory.bookRoom("Single Room");
        inventory.bookRoom("Single Room");
        System.out.println("  Remaining Single Rooms: " + inventory.getAvailability("Single Room"));
        
        // Step 6: Display inventory after bookings
        System.out.println("\n[STEP 6] Displaying Inventory After Bookings...");
        inventory.displayInventory();
        
        // Step 7: Demonstrate cancellation
        System.out.println("[STEP 7] Processing Booking Cancellations...");
        boolean singleCancelled = inventory.cancelBooking("Single Room");
        System.out.println("✓ Single Room booking cancelled: " + (singleCancelled ? "Success" : "Failed"));
        System.out.println("  Available Single Rooms: " + inventory.getAvailability("Single Room"));
        
        boolean suiteCancelled = inventory.cancelBooking("Suite Room");
        System.out.println("✓ Suite Room booking cancelled: " + (suiteCancelled ? "Success" : "Failed"));
        System.out.println("  Available Suite Rooms: " + inventory.getAvailability("Suite Room"));
        
        // Step 8: Display final inventory state
        System.out.println("\n[STEP 8] Displaying Final Inventory State...");
        inventory.displayInventory();
        
        // Step 9: Demonstrate scalability with a new room type
        System.out.println("[STEP 9] Demonstrating Scalability - Adding New Room Type...");
        inventory.addRoom("Deluxe Room", 5);
        System.out.println("✓ Deluxe Room: 5 rooms added to inventory");
        System.out.println("  (Note: No changes required in existing application logic)");
        inventory.displayInventory();
        
        // Summary
        System.out.println("========================================");
        System.out.println("SUMMARY - Benefits of Centralized Inventory");
        System.out.println("========================================");
        System.out.println("✓ Single Source of Truth: One HashMap manages all availability");
        System.out.println("✓ O(1) Operations: Constant-time access and updates");
        System.out.println("✓ Scalability: Easy to add new room types");
        System.out.println("✓ Consistency: All operations go through RoomInventory");
        System.out.println("✓ Encapsulation: Inventory logic is contained and controlled");
        System.out.println("✓ Total Rooms Available: " + inventory.getTotalAvailability());
        System.out.println("========================================");
        System.out.println("Application terminated successfully!");
    }
}
