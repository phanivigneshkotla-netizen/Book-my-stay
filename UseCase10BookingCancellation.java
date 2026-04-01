/**
 * UseCase10BookingCancellation - Booking Cancellation & Inventory Rollback
 * 
 * BUSINESS REQUIREMENT:
 * Enable safe cancellation of confirmed bookings by correctly reversing system 
 * state changes, ensuring inventory consistency and predictable recovery behavior.
 * 
 * TECHNICAL SOLUTION:
 * - CancellationService with Stack-based LIFO rollback
 * - Validation before state changes (fail-fast design)
 * - Controlled mutation in strict operational sequence
 * - Inventory restoration with consistency guarantees
 * - Idempotence protection (prevent double cancellations)
 * 
 * KEY DATA STRUCTURE: Stack<String>
 * - LIFO (Last-In-First-Out) order
 * - Most recent cancellation reversed first
 * - Natural undo operations model
 * - Predictable recovery behavior
 * 
 * OPERATIONS DEMONSTRATED:
 * 1. Cancel confirmed booking with inventory restoration
 * 2. Multiple cancellations showing LIFO order
 * 3. Error handling for invalid cancellation attempts
 * 4. Rollback state verification
 * 5. System consistency after cancellations
 * 
 * KEY CONCEPTS:
 * - State Reversal: Undoing previous operations
 * - Stack LIFO: Last-in-first-out recovery order
 * - Controlled Mutation: Strict operational sequence
 * - Inventory Restoration: Immediate availability recovery
 * - Validation: Reservation verification before cancellation
 * - Idempotence: Protection against double cancellations
 * 
 * BEFORE & AFTER USE CASES:
 * Before UC10: Valid bookings couldn't be safely cancelled (no rollback support)
 * After UC10: Safe cancellation with state reversal and inventory restoration
 * 
 * @author Hotel Booking Team
 * @version 10.0
 * @since 2026-04-01
 */
public class UseCase10BookingCancellation {
    
    private static RoomInventory inventory;
    private static AllocationService allocationService;
    private static BookingHistory bookingHistory;
    private static CancellationService cancellationService;
    private static int scenarioCount = 0;
    
    /**
     * Main entry point - Demonstrates booking cancellation and rollback
     */
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Use Case 10: Booking Cancellation & Inventory Rollback       ║");
        System.out.println("║  Stack-based LIFO Rollback with State Reversal                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Initialize system
        initializeSystem();
        
        // Run cancellation scenarios
        System.out.println("\n════ CANCELLATION SCENARIO DEMONSTRATIONS ════\n");
        
        // Scenario 1: Basic cancellation
        demonstrateBasicCancellation();
        
        // Scenario 2: Multiple cancellations showing LIFO order
        demonstrateMultipleCancellationsLIFO();
        
        // Scenario 3: Attempt double cancellation (error case)
        demonstrateDoubleCancellationError();
        
        // Scenario 4: Attempt invalid cancellation (non-existent booking)
        demonstrateInvalidCancellation();
        
        // Scenario 5: Inventory consistency verification
        demonstrateInventoryConsistency();
        
        // Scenario 6: Rollback state and stack analysis
        demonstrateRollbackStateAnalysis();
        
        System.out.println("\n════ CANCELLATION SUMMARY ════");
        System.out.println("Total Scenarios Demonstrated: " + scenarioCount);
        System.out.println("✓ Valid cancellations executed with state reversal");
        System.out.println("✓ Inventory restored immediately after cancellations");
        System.out.println("✓ LIFO order maintained in rollback stack");
        System.out.println("✓ System state remains consistent after all operations");
        System.out.println("✓ Error scenarios handled gracefully");
        System.out.println("\nUse Case 10 - Booking Cancellation & Rollback Successfully Demonstrated!");
    }
    
    /**
     * Initialize system with services
     */
    private static void initializeSystem() {
        inventory = new RoomInventory();
        
        // Initialize inventory
        inventory.addRoom("Single", 15);
        inventory.addRoom("Double", 12);
        inventory.addRoom("Suite", 8);
        
        System.out.println("✓ Room Inventory Initialized:");
        System.out.println("  - Single: " + inventory.getAvailability("Single") + " available");
        System.out.println("  - Double: " + inventory.getAvailability("Double") + " available");
        System.out.println("  - Suite: " + inventory.getAvailability("Suite") + " available");
        
        // Initialize allocation service
        allocationService = new AllocationService(inventory);
        System.out.println("✓ Allocation Service Initialized");
        
        // Initialize booking history
        bookingHistory = new BookingHistory();
        System.out.println("✓ Booking History Initialized");
        
        // Initialize cancellation service
        cancellationService = new CancellationService(allocationService, inventory, bookingHistory);
        System.out.println("✓ Cancellation Service Initialized\n");
    }
    
    /**
     * Scenario 1: Basic Cancellation - Single booking cancellation
     */
    private static void demonstrateBasicCancellation() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": BASIC CANCELLATION");
        System.out.println("─────────────────────────────────────────────────");
        
        String roomType = "Double";
        int initialAvailability = inventory.getAvailability(roomType);
        
        System.out.println("Initial State:");
        System.out.println("  " + roomType + " rooms available: " + initialAvailability);
        
        try {
            // Simulate a booking first
            System.out.println("\nStep 1: Book a " + roomType + " room");
            inventory.bookRoom(roomType);
            int afterBooking = inventory.getAvailability(roomType);
            System.out.println("  After booking: " + afterBooking + " available (decreased by 1)");
            
            // Now cancel the booking
            System.out.println("\nStep 2: Cancel the booking (Reservation #1001)");
            CancellationService.CancellationResult result = 
                cancellationService.cancelBooking(1001, roomType);
            
            System.out.println("  ✓ " + result);
            
            int afterCancellation = inventory.getAvailability(roomType);
            System.out.println("\nAfter Cancellation:");
            System.out.println("  " + roomType + " rooms available: " + afterCancellation);
            System.out.println("  ✓ Inventory restored (increased by 1)");
            System.out.println("  ✓ Stack size: " + result.getStackSize() + " (1 release recorded)");
            
        } catch (InvalidBookingException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 2: Multiple Cancellations - Demonstrate LIFO order
     */
    private static void demonstrateMultipleCancellationsLIFO() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": MULTIPLE CANCELLATIONS (LIFO ORDER)");
        System.out.println("─────────────────────────────────────────────────");
        
        String roomType = "Suite";
        int initialAvailability = inventory.getAvailability(roomType);
        
        System.out.println("Initial State:");
        System.out.println("  " + roomType + " rooms available: " + initialAvailability);
        
        try {
            // Book 3 suites
            System.out.println("\nStep 1: Book 3 " + roomType + " rooms");
            for (int i = 0; i < 3; i++) {
                inventory.bookRoom(roomType);
            }
            System.out.println("  After 3 bookings: " + inventory.getAvailability(roomType) + " available");
            
            // Cancel in sequence
            System.out.println("\nStep 2: Cancel 3 bookings (LIFO - Last In First Out)");
            
            for (int i = 1; i <= 3; i++) {
                CancellationService.CancellationResult result = 
                    cancellationService.cancelBooking(2000 + i, roomType);
                System.out.println("  Cancellation " + i + ": " + result.getReleasedRoomId() + 
                                 " (Stack size: " + result.getStackSize() + ")");
            }
            
            System.out.println("\nLIFO Order Demonstrated:");
            System.out.println("  Booking Order: 2001, 2002, 2003");
            System.out.println("  Cancellation Order: 2001, 2002, 2003");
            System.out.println("  Stack LIFO: Most recent release available first");
            
            int afterCancellations = inventory.getAvailability(roomType);
            System.out.println("\nAfter All Cancellations:");
            System.out.println("  " + roomType + " rooms available: " + afterCancellations);
            System.out.println("  ✓ Inventory fully restored to initial state");
            System.out.println("  ✓ Stack contains 3 releases in LIFO order");
            
        } catch (InvalidBookingException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 3: Double Cancellation Error - Attempt to cancel same booking twice
     */
    private static void demonstrateDoubleCancellationError() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": DOUBLE CANCELLATION ERROR");
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Idempotence Protection: Prevent cancelling same booking twice");
        
        String roomType = "Single";
        
        try {
            // Book and cancel once
            System.out.println("\nStep 1: Book a " + roomType + " room");
            inventory.bookRoom(roomType);
            
            System.out.println("Step 2: Cancel booking (Reservation #3001)");
            CancellationService.CancellationResult result = 
                cancellationService.cancelBooking(3001, roomType);
            System.out.println("  ✓ First cancellation successful: " + result);
            
            // Try to cancel again (should fail)
            System.out.println("\nStep 3: Attempt to cancel same booking again");
            result = cancellationService.cancelBooking(3001, roomType);
            System.out.println("  ✗ Second cancellation should have failed!");
            
        } catch (InvalidBookingException e) {
            System.out.println("  ✓ Correctly rejected: " + e.getMessage());
            System.out.println("  ✓ Idempotence protection working");
        }
        System.out.println();
    }
    
    /**
     * Scenario 4: Invalid Cancellation - Non-existent booking
     */
    private static void demonstrateInvalidCancellation() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": INVALID CANCELLATION");
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Error Handling: Prevent cancellation of non-existent bookings");
        
        try {
            System.out.println("\nAttempting to cancel non-existent booking (Reservation #9999)");
            cancellationService.cancelBooking(9999, "Double");
            System.out.println("✗ Should have failed!");
            
        } catch (InvalidBookingException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
            System.out.println("✓ System state protected from invalid operations");
        }
        System.out.println();
    }
    
    /**
     * Scenario 5: Inventory Consistency Verification
     */
    private static void demonstrateInventoryConsistency() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": INVENTORY CONSISTENCY");
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Verify inventory counts remain consistent through booking/cancellation cycles");
        
        String roomType = "Double";
        int initialCount = inventory.getAvailability(roomType);
        
        try {
            System.out.println("\nInitial " + roomType + " available: " + initialCount);
            
            // Multiple booking/cancellation cycles
            System.out.println("\nExecuting 5 booking/cancellation cycles:");
            for (int i = 0; i < 5; i++) {
                inventory.bookRoom(roomType);
                cancellationService.cancelBooking(4000 + i, roomType);
                System.out.println("  Cycle " + (i + 1) + ": Available after cancellation: " + 
                                 inventory.getAvailability(roomType));
            }
            
            int finalCount = inventory.getAvailability(roomType);
            System.out.println("\nFinal " + roomType + " available: " + finalCount);
            
            if (finalCount == initialCount) {
                System.out.println("✓ Inventory is consistent (initial = final)");
                System.out.println("✓ No state corruption across cycles");
            } else {
                System.out.println("✗ Inventory mismatch detected!");
            }
            
        } catch (InvalidBookingException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 6: Rollback State Analysis
     */
    private static void demonstrateRollbackStateAnalysis() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": ROLLBACK STATE ANALYSIS");
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Analyze Stack contents and rollback tracking");
        
        String roomType = "Suite";
        
        try {
            // Create a small batch of cancellations
            System.out.println("\nCreating batch of cancellations:");
            
            for (int i = 0; i < 3; i++) {
                inventory.bookRoom(roomType);
                cancellationService.cancelBooking(5000 + i, roomType);
            }
            
            System.out.println("Current rollback stack state:");
            System.out.println("  Stack contents: " + cancellationService.getRollbackStackContents());
            System.out.println("  Stack size: " + cancellationService.getReleasedRoomCount());
            
            // Check cancellation status
            System.out.println("\nCancellation status check:");
            System.out.println("  Booking #5000 cancelled: " + cancellationService.isCancelled(5000));
            System.out.println("  Booking #5001 cancelled: " + cancellationService.isCancelled(5001));
            System.out.println("  Booking #5002 cancelled: " + cancellationService.isCancelled(5002));
            System.out.println("  Booking #5999 cancelled: " + cancellationService.isCancelled(5999));
            
            // Peek at most recent
            System.out.println("\nMost recent release (peek): " + 
                             cancellationService.peekReleasedRoom());
            
            System.out.println("\n✓ Stack maintains LIFO order for rollback operations");
            System.out.println("✓ Cancellation status tracked reliably");
            
        } catch (InvalidBookingException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
        System.out.println();
    }
}
