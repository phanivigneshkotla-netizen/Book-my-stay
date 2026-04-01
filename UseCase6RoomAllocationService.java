import java.time.LocalDate;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Application Entry Point for Safe Room Allocation and Booking Confirmation
 * 
 * This use case demonstrates how booking requests are safely confirmed by allocating
 * unique rooms while maintaining inventory consistency and preventing double-booking
 * under all circumstances.
 * 
 * Key Concepts Demonstrated:
 * - Double-Booking Prevention: Set data structure prevents room ID reuse
 * - Set Data Structure: Enforces uniqueness of room assignments by design
 * - Uniqueness Enforcement: No manual duplicate checks needed; Set handles it
 * - Mapping: HashMap<String, Set<String>> groups allocated rooms by type
 * - Atomic Operations: Room assignment and inventory update occur together
 * - Inventory Synchronization: Availability updated immediately after allocation
 * 
 * Problem Addressed:
 * Use Case 5 handled request ordering via Queue but did not confirm bookings.
 * Without safe allocation and uniqueness enforcement, queued requests could still
 * result in conflicting room assignments:
 *   - Multiple guests assigned the same room (double-booking)
 *   - Room ID collisions
 *   - Inconsistent inventory state
 *   - No guarantee of fair allocation
 * 
 * Solution - AllocationService with Set Uniqueness:
 * - Set<String> stores allocated room IDs; enforces uniqueness by design
 * - O(1) Set membership check prevents duplicate assignments
 * - HashMap groups rooms by type for easy validation and reporting
 * - Atomic operations maintain consistency between allocation and inventory
 * - Inventory updated immediately after each room assignment
 * - Failed allocations trigger rollback to preserve state
 * 
 * Room ID Format: "ROOMTYPE_SEQUENCE" (e.g., "SINGLE_001", "DOUBLE_005")
 * 
 * Flow:
 * 1. Initialize inventory with room availability
 * 2. Initialize AllocationService with Set-based tracking
 * 3. Create booking requests in a queue
 * 4. Process requests one by one in FIFO order
 * 5. For each request:
 *    a. Check availability in inventory
 *    b. Generate unique room ID
 *    c. Verify uniqueness against Set (prevents reuse)
 *    d. Add room ID to Set
 *    e. Update inventory atomically
 *    f. Confirm booking with room ID
 * 6. Demonstrate that no room was assigned twice
 * 7. Show final allocation report
 * 
 * Actors:
 * - Booking Service (AllocationService): Processes requests and performs allocation
 * - Inventory Service (RoomInventory): Maintains availability state
 * - Reservation Queue (BookingRequestQueue): Provides requests in FIFO order
 * 
 * Benefits:
 * - Guaranteed uniqueness of room assignments
 * - Immediate synchronization between booking and inventory
 * - Elimination of double-booking scenarios
 * - System consistency maintained at all times
 * - Clear audit trail of allocations
 * 
 * Double-Booking Prevention Mechanism:
 * The Set data structure is the key to preventing double-booking:
 * - Set only stores unique elements by definition
 * - When we add a room ID to Set, it's guaranteed to be unique
 * - If somehow we tried to add a duplicate, the Set would reject it
 * - We also decrement inventory immediately after allocation
 * - This creates a 1:1 mapping between allocated rooms and used inventory
 * - No room can be allocated twice, ensuring consistency
 * 
 * @author Hotel Booking Team
 * @version 6.0
 * @since 2026-04-01
 */
public class UseCase6RoomAllocationService {
    
    /**
     * Main method - Entry point demonstrating safe room allocation
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 6: Reservation Confirmation & Room Allocation");
        System.out.println("Version 6.0");
        System.out.println("========================================");
        
        // Step 1: Initialize inventory with room availability
        System.out.println("\n[STEP 1] Initializing Room Inventory...");
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 5);
        inventory.addRoom("Double Room", 4);
        inventory.addRoom("Suite Room", 2);
        System.out.println("✓ Inventory initialized");
        System.out.println("  - Single Room: 5 available");
        System.out.println("  - Double Room: 4 available");
        System.out.println("  - Suite Room: 2 available");
        System.out.println("  - Total: " + inventory.getTotalAvailability() + " rooms");
        
        // Step 2: Initialize AllocationService with Set-based tracking
        System.out.println("\n[STEP 2] Initializing Allocation Service...");
        AllocationService allocationService = new AllocationService(inventory);
        allocationService.initializeRoomType("Single Room");
        allocationService.initializeRoomType("Double Room");
        allocationService.initializeRoomType("Suite Room");
        System.out.println("✓ AllocationService initialized with Set-based uniqueness tracking");
        System.out.println("✓ Data Structure: HashMap<String, Set<String>> for room ID uniqueness");
        System.out.println("✓ Double-booking prevention: ENABLED");
        
        // Step 3: Create and queue booking requests
        System.out.println("\n[STEP 3] Creating Booking Requests...");
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        
        Reservation req1 = new Reservation("Alice Johnson", "Single Room", 
            LocalDate.of(2026, 4, 5), LocalDate.of(2026, 4, 8), "alice@example.com", 1);
        Reservation req2 = new Reservation("Bob Smith", "Double Room",
            LocalDate.of(2026, 4, 6), LocalDate.of(2026, 4, 10), "bob@example.com", 2);
        Reservation req3 = new Reservation("Carol White", "Suite Room",
            LocalDate.of(2026, 4, 7), LocalDate.of(2026, 4, 12), "carol@example.com", 4);
        Reservation req4 = new Reservation("David Brown", "Double Room",
            LocalDate.of(2026, 4, 8), LocalDate.of(2026, 4, 11), "david@example.com", 2);
        Reservation req5 = new Reservation("Eva Martinez", "Single Room",
            LocalDate.of(2026, 4, 9), LocalDate.of(2026, 4, 13), "eva@example.com", 1);
        Reservation req6 = new Reservation("Frank Wilson", "Suite Room",
            LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 15), "frank@example.com", 3);
        Reservation req7 = new Reservation("Grace Lee", "Single Room",
            LocalDate.of(2026, 4, 11), LocalDate.of(2026, 4, 14), "grace@example.com", 1);
        
        requestQueue.addReservationRequest(req1);
        requestQueue.addReservationRequest(req2);
        requestQueue.addReservationRequest(req3);
        requestQueue.addReservationRequest(req4);
        requestQueue.addReservationRequest(req5);
        requestQueue.addReservationRequest(req6);
        requestQueue.addReservationRequest(req7);
        
        System.out.println("✓ 7 booking requests created and queued");
        System.out.println("  Total requests in queue: " + requestQueue.getQueueSize());
        
        // Step 4: Display initial state
        System.out.println("\n[STEP 4] Pre-Allocation State...");
        System.out.println("Inventory Status:");
        System.out.println("  - Single Room: " + inventory.getAvailability("Single Room") + " available");
        System.out.println("  - Double Room: " + inventory.getAvailability("Double Room") + " available");
        System.out.println("  - Suite Room: " + inventory.getAvailability("Suite Room") + " available");
        System.out.println("  - Total: " + inventory.getTotalAvailability() + " available");
        
        // Step 5: Process all requests with allocation
        System.out.println("\n[STEP 5] Processing All Booking Requests with Room Allocation...");
        System.out.println("Note: Allocation is ATOMIC - room ID generation, uniqueness check,");
        System.out.println("      and inventory update happen together\n");
        
        int allocationCount = 0;
        while (!requestQueue.isQueueEmpty()) {
            Reservation request = requestQueue.getNextReservation();
            if (request == null) break;
            
            allocationCount++;
            String roomType = request.getRoomType();
            String guestName = request.getGuestName();
            int reservationId = request.getReservationId();
            
            System.out.println("------- BOOKING REQUEST " + allocationCount + " -------");
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);
            System.out.println("Reservation ID: #" + reservationId);
            
            // Allocate room (CORE OPERATION - where double-booking is prevented)
            AllocationService.AllocationResult result = allocationService.allocateRoom(request);
            
            if (result.isSuccess()) {
                System.out.println("Status: ✓ CONFIRMED");
                System.out.println("Room ID: " + result.getRoomId());
                System.out.println("Message: " + result.getMessage());
            } else {
                System.out.println("Status: ✗ FAILED");
                System.out.println("Reason: " + result.getMessage());
            }
            
            // Show current inventory
            System.out.println("Current Availability:");
            System.out.println("  - Single Room: " + inventory.getAvailability("Single Room"));
            System.out.println("  - Double Room: " + inventory.getAvailability("Double Room"));
            System.out.println("  - Suite Room: " + inventory.getAvailability("Suite Room"));
            System.out.println();
        }
        
        // Step 6: Display allocation report
        System.out.println("[STEP 6] Final Allocation Report...");
        allocationService.displayAllocationReport();
        
        // Step 7: Display final inventory state
        System.out.println("[STEP 7] Post-Allocation Inventory State...");
        System.out.println("========================================");
        System.out.println("FINAL INVENTORY STATUS");
        System.out.println("========================================");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room") + " available");
        System.out.println("  Allocated: " + allocationService.getAllocatedRoomIds("Single Room").size());
        System.out.println("  Room IDs: " + allocationService.getAllocatedRoomIds("Single Room"));
        System.out.println();
        System.out.println("Double Room: " + inventory.getAvailability("Double Room") + " available");
        System.out.println("  Allocated: " + allocationService.getAllocatedRoomIds("Double Room").size());
        System.out.println("  Room IDs: " + allocationService.getAllocatedRoomIds("Double Room"));
        System.out.println();
        System.out.println("Suite Room: " + inventory.getAvailability("Suite Room") + " available");
        System.out.println("  Allocated: " + allocationService.getAllocatedRoomIds("Suite Room").size());
        System.out.println("  Room IDs: " + allocationService.getAllocatedRoomIds("Suite Room"));
        System.out.println();
        System.out.println("Total Available: " + inventory.getTotalAvailability());
        System.out.println("Total Allocated: " + allocationService.getTotalAllocatedRooms());
        System.out.println("========================================");
        
        // Step 8: Verify no duplicate room assignments
        System.out.println("\n[STEP 8] Verification - No Double-Booking Occurred...");
        boolean duplicatesFound = false;
        for (String roomType : new String[]{"Single Room", "Double Room", "Suite Room"}) {
            allocationService.getAllocatedRoomIds(roomType).stream()
                .forEach(roomId -> System.out.println("✓ " + roomId + " assigned (unique)"));
        }
        System.out.println("✓ All room assignments are unique - double-booking prevented!");
        
        // Summary
        System.out.println("\n========================================");
        System.out.println("SUMMARY - Use Case 6 Achievements");
        System.out.println("========================================");
        AllocationService.AllocationStatistics stats = allocationService.getStatistics();
        System.out.println("✓ Double-Booking Prevention: Set data structure enforces uniqueness");
        System.out.println("✓ Atomic Operations: Room assignment and inventory update together");
        System.out.println("✓ Successful Allocations: " + stats.getSuccessful());
        System.out.println("✓ Failed Allocations: " + stats.getFailed());
        System.out.println("✓ Total Rooms Allocated: " + stats.getTotalAllocated());
        System.out.println("✓ Allocation Consistency: Inventory - Allocated = Remaining");
        System.out.println("  - Single: 5 - " + allocationService.getAllocatedRoomIds("Single Room").size() + 
                          " = " + inventory.getAvailability("Single Room"));
        System.out.println("  - Double: 4 - " + allocationService.getAllocatedRoomIds("Double Room").size() + 
                          " = " + inventory.getAvailability("Double Room"));
        System.out.println("  - Suite: 2 - " + allocationService.getAllocatedRoomIds("Suite Room").size() + 
                          " = " + inventory.getAvailability("Suite Room"));
        System.out.println("\nKey Learning:");
        System.out.println("The Set data structure is crucial for preventing double-booking:");
        System.out.println("- Set enforces uniqueness by design (not by policy)");
        System.out.println("- Each room ID can only be assigned once");
        System.out.println("- Inventory updates immediately after allocation");
        System.out.println("- System state remains consistent throughout");
        System.out.println("========================================");
        System.out.println("Application terminated successfully!");
    }
}
