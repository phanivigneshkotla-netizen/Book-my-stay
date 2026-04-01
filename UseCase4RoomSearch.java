import java.util.HashMap;

/**
 * Use Case 4: Room Search & Availability Check
 * Application Entry Point for Room Search and Availability Display
 * 
 * This use case demonstrates how guests can search for available rooms and view their details
 * without modifying the system state. It reinforces safe data access and clear separation of
 * responsibilities between read-only operations (search) and write operations (booking).
 * 
 * Key Concepts Demonstrated:
 * - Read-Only Access: Search operations only read; no inventory modifications occur
 * - Defensive Programming: Validates data and checks before displaying results
 * - Separation of Concerns: Search logic isolated from booking and inventory mutation
 * - Inventory as State Holder: Accesses inventory only to check availability
 * - Domain Model Usage: Uses Room objects for descriptive information
 * - Validation Logic: Filters out rooms with zero availability
 * 
 * Problem Addressed:
 * Use Case 3 introduced centralized inventory management but did not differentiate
 * between read and write access. Without explicit separation, inventory could be
 * accidentally modified during non-booking operations.
 * 
 * Solution - SearchService:
 * - Provides read-only access to inventory
 * - Never modifies inventory state during searches
 * - Validates availability before displaying results
 * - Encapsulates search logic from booking logic
 * - Enables concurrent searches without conflicts
 * 
 * Flow:
 * 1. Initialize inventory with room types and availability
 * 2. Create SearchService with access to inventory
 * 3. Register room types with SearchService
 * 4. Perform searches to view available rooms
 * 5. Display room details and pricing
 * 6. Verify that inventory remains unchanged
 * 
 * Actor:
 * - Guest: Initiates room search requests
 * - SearchService: Handles read-only access to inventory and room information
 * 
 * Benefits:
 * - Accurate availability visibility without state mutation
 * - Reduced risk of accidental inventory corruption
 * - Clear separation between read-only and write operations
 * - Safe for concurrent guest searches without conflicts
 * 
 * @author Hotel Booking Team
 * @version 4.0
 * @since 2026-04-01
 */
public class UseCase4RoomSearch {
    
    /**
     * Main method - Entry point demonstrating room search functionality
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 4: Room Search & Availability Check");
        System.out.println("Version 4.0");
        System.out.println("========================================");
        
        // Step 1: Initialize the centralized inventory
        System.out.println("\n[STEP 1] Initializing Room Inventory...");
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 10);
        inventory.addRoom("Double Room", 8);
        inventory.addRoom("Suite Room", 3);
        System.out.println("✓ Inventory initialized with 3 room types");
        System.out.println("✓ Total Available Rooms: " + inventory.getTotalAvailability());
        
        // Step 2: Initialize SearchService (read-only access layer)
        System.out.println("\n[STEP 2] Initializing Search Service...");
        SearchService searchService = new SearchService(inventory);
        System.out.println("✓ SearchService initialized (read-only access to inventory)");
        
        // Step 3: Register room types with SearchService
        System.out.println("\n[STEP 3] Registering Room Types with Search Service...");
        searchService.registerRoomType("Single Room", new SingleRoom("Queen"));
        System.out.println("✓ Single Room registered");
        
        searchService.registerRoomType("Double Room", new DoubleRoom("City View"));
        System.out.println("✓ Double Room registered");
        
        searchService.registerRoomType("Suite Room", new SuiteRoom(2));
        System.out.println("✓ Suite Room registered");
        
        // Step 4: Display all room details before any bookings
        System.out.println("\n[STEP 4] Displaying All Room Details (Read-Only Operation)...");
        searchService.displayAllRoomDetails();
        
        // Step 5: Perform initial search
        System.out.println("[STEP 5] Guest #1 - Initial Room Search...");
        System.out.println("Searching for available rooms...");
        searchService.displaySearchResults();
        
        // Step 6: Check availability for specific rooms
        System.out.println("[STEP 6] Checking Specific Room Availability (Read-Only)...");
        System.out.println("Is Single Room available? " + (searchService.isRoomAvailable("Single Room") ? "YES" : "NO"));
        System.out.println("Single Room Availability: " + searchService.getAvailability("Single Room") + " rooms");
        System.out.println();
        System.out.println("Is Double Room available? " + (searchService.isRoomAvailable("Double Room") ? "YES" : "NO"));
        System.out.println("Double Room Availability: " + searchService.getAvailability("Double Room") + " rooms");
        System.out.println();
        System.out.println("Is Suite Room available? " + (searchService.isRoomAvailable("Suite Room") ? "YES" : "NO"));
        System.out.println("Suite Room Availability: " + searchService.getAvailability("Suite Room") + " rooms");
        
        // Step 7: Display availability summary
        System.out.println("\n[STEP 7] Availability Summary from Search (Read-Only)...");
        searchService.displayAvailabilitySummary();
        
        // Step 8: Simulate some bookings (to demonstrate state change)
        System.out.println("[STEP 8] Simulating Bookings (Inventory is modified)...");
        System.out.println("Booking: 5 Single Rooms");
        for (int i = 0; i < 5; i++) {
            inventory.bookRoom("Single Room");
        }
        System.out.println("✓ 5 Single Rooms booked");
        
        System.out.println("Booking: 3 Double Rooms");
        for (int i = 0; i < 3; i++) {
            inventory.bookRoom("Double Room");
        }
        System.out.println("✓ 3 Double Rooms booked");
        
        System.out.println("Booking: 2 Suite Rooms");
        for (int i = 0; i < 2; i++) {
            inventory.bookRoom("Suite Room");
        }
        System.out.println("✓ 2 Suite Rooms booked");
        
        // Step 9: Perform search again to verify read-only access reflects new state
        System.out.println("\n[STEP 9] Guest #2 - Search After Bookings (Read-Only, Shows Updated State)...");
        System.out.println("Note: SearchService reflects updated inventory without modifying it");
        searchService.displaySearchResults();
        
        // Step 10: Demonstrate that search operations don't modify inventory
        System.out.println("[STEP 10] Performing Multiple Searches (Inventory Remains Unchanged)...");
        System.out.println("Performing Search 1...");
        searchService.searchAvailableRooms();
        System.out.println("✓ Search completed. Inventory status:");
        System.out.println("  Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("  Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("  Suite Room: " + inventory.getAvailability("Suite Room"));
        
        System.out.println("\nPerforming Search 2...");
        searchService.searchAvailableRooms();
        System.out.println("✓ Search completed. Inventory status:");
        System.out.println("  Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("  Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("  Suite Room: " + inventory.getAvailability("Suite Room"));
        
        System.out.println("\nPerforming Search 3...");
        searchService.searchAvailableRooms();
        System.out.println("✓ Search completed. Inventory status:");
        System.out.println("  Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("  Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("  Suite Room: " + inventory.getAvailability("Suite Room"));
        System.out.println("\n✓ Multiple searches performed without modifying inventory!");
        
        // Step 11: Book remaining Suite Rooms to demonstrate unavailable status
        System.out.println("\n[STEP 11] Booking Last Suite Room...");
        inventory.bookRoom("Suite Room");
        System.out.println("✓ Last Suite Room booked");
        System.out.println("Suite Room availability: " + inventory.getAvailability("Suite Room"));
        
        // Step 12: Final search showing unavailable rooms
        System.out.println("\n[STEP 12] Guest #3 - Final Search (Some Rooms No Longer Available)...");
        System.out.println("Note: Only rooms with availability > 0 are displayed");
        searchService.displaySearchResults();
        
        // Summary
        System.out.println("========================================");
        System.out.println("SUMMARY - Use Case 4 Achievements");
        System.out.println("========================================");
        System.out.println("✓ Read-Only Access: Search never modifies inventory");
        System.out.println("✓ Defensive Programming: Only available rooms displayed");
        System.out.println("✓ Separation of Concerns: Search isolated from booking");
        System.out.println("✓ Clear Boundary: Search logic separated from write operations");
        System.out.println("✓ Concurrent Safety: Multiple searches don't interfere");
        System.out.println("✓ Final Inventory State:");
        System.out.println("  - Single Rooms: " + inventory.getAvailability("Single Room") + " available");
        System.out.println("  - Double Rooms: " + inventory.getAvailability("Double Room") + " available");
        System.out.println("  - Suite Rooms: " + inventory.getAvailability("Suite Room") + " available");
        System.out.println("  - Total: " + inventory.getTotalAvailability() + " available");
        System.out.println("========================================");
        System.out.println("Application terminated successfully!");
    }
}
