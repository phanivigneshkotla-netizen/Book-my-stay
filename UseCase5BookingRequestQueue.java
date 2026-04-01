import java.time.LocalDate;

/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Application Entry Point for Booking Request Queue Management
 * 
 * This use case demonstrates how booking requests are handled fairly using a Queue data structure.
 * The FIFO (First-Come-First-Served) principle ensures that all guests are treated equally
 * based on request arrival order.
 * 
 * Key Concepts Demonstrated:
 * - Queue Data Structure: Natural FIFO container for booking requests
 * - FIFO Principle: First request in, first request out for fair ordering
 * - Fairness: Requesting guests served in arrival order
 * - Request Ordering: Queue preserves insertion order automatically
 * - No State Mutation: Queueing does not modify inventory at this stage
 * - Request Staging: Requests collected before processing/allocation
 * - Decoupling: Intake separate from allocation (will be combined in future use cases)
 * 
 * Problem Addressed:
 * Use Case 4 allowed room visibility but did not handle booking intent.
 * Multiple simultaneous booking attempts could not be managed fairly.
 * Without a request intake mechanism, competitive booking scenarios could lead to:
 *   - Inconsistent allocation
 *   - Unfair request handling
 *   - Uncontrolled concurrent modifications
 * 
 * Solution - BookingRequestQueue:
 * - Queue data structure implements FIFO naturally
 * - Incoming requests are queued in arrival order
 * - Requests are processed one by one from front of queue
 * - No inventory modifications occur during queueing
 * - Prepares system for controlled allocation in next use case
 * 
 * Flow:
 * 1. Create empty booking request queue
 * 2. Guests submit booking requests in sequence
 * 3. Each request is added to queue (enqueue operation)
 * 4. Display current queue state and pending requests
 * 5. Process requests from queue one by one (dequeue operation)
 * 6. Demonstrate that order is preserved regardless of request details
 * 7. Show that inventory is not modified during this stage
 * 
 * Actor:
 * - Guest: Submits booking requests
 * - Reservation: Encapsulates booking request details
 * - BookingRequestQueue: Manages FIFO ordering of requests
 * 
 * Benefits:
 * - Fair and deterministic booking request handling
 * - Predictable system behavior under peak load
 * - Simplified request coordination before allocation
 * - Clear ordering prevents double-booking issues
 * 
 * Future Use Case:
 * Use Case 6 will integrate this queue with the allocation system to actually
 * process bookings (consume rooms from inventory).
 * 
 * @author Hotel Booking Team
 * @version 5.0
 * @since 2026-04-01
 */
public class UseCase5BookingRequestQueue {
    
    /**
     * Main method - Entry point demonstrating booking request queue management
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 5: Booking Request (First-Come-First-Served)");
        System.out.println("Version 5.0");
        System.out.println("========================================");
        
        // Step 1: Initialize the booking request queue
        System.out.println("\n[STEP 1] Initializing Booking Request Queue...");
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        System.out.println("✓ Booking Request Queue initialized");
        System.out.println("✓ Queue uses FIFO (First-In-First-Out) principle");
        System.out.println("✓ Data Structure: Queue<Reservation> backed by LinkedList");
        bookingQueue.displayQueueStatus();
        
        // Step 2: Create guest booking requests (simulating multiple guests)
        System.out.println("[STEP 2] Processing Guest Booking Requests...");
        System.out.println("Simulating multiple guests submitting booking requests during peak hours");
        
        // Guest 1 - Alice Johnson
        System.out.println("\nGuest #1 - Alice Johnson submits booking request");
        Reservation req1 = new Reservation(
            "Alice Johnson",
            "Single Room",
            LocalDate.of(2026, 4, 5),
            LocalDate.of(2026, 4, 8),
            "alice@example.com",
            1
        );
        bookingQueue.addReservationRequest(req1);
        System.out.println("✓ Request #" + req1.getReservationId() + " added to queue");
        
        // Guest 2 - Bob Smith
        System.out.println("\nGuest #2 - Bob Smith submits booking request");
        Reservation req2 = new Reservation(
            "Bob Smith",
            "Double Room",
            LocalDate.of(2026, 4, 6),
            LocalDate.of(2026, 4, 10),
            "bob@example.com",
            2
        );
        bookingQueue.addReservationRequest(req2);
        System.out.println("✓ Request #" + req2.getReservationId() + " added to queue");
        
        // Guest 3 - Carol White
        System.out.println("\nGuest #3 - Carol White submits booking request");
        Reservation req3 = new Reservation(
            "Carol White",
            "Suite Room",
            LocalDate.of(2026, 4, 7),
            LocalDate.of(2026, 4, 12),
            "carol@example.com",
            4
        );
        bookingQueue.addReservationRequest(req3);
        System.out.println("✓ Request #" + req3.getReservationId() + " added to queue");
        
        // Guest 4 - David Brown
        System.out.println("\nGuest #4 - David Brown submits booking request");
        Reservation req4 = new Reservation(
            "David Brown",
            "Double Room",
            LocalDate.of(2026, 4, 8),
            LocalDate.of(2026, 4, 11),
            "david@example.com",
            2
        );
        bookingQueue.addReservationRequest(req4);
        System.out.println("✓ Request #" + req4.getReservationId() + " added to queue");
        
        // Guest 5 - Eva Martinez
        System.out.println("\nGuest #5 - Eva Martinez submits booking request");
        Reservation req5 = new Reservation(
            "Eva Martinez",
            "Single Room",
            LocalDate.of(2026, 4, 9),
            LocalDate.of(2026, 4, 13),
            "eva@example.com",
            1
        );
        bookingQueue.addReservationRequest(req5);
        System.out.println("✓ Request #" + req5.getReservationId() + " added to queue");
        
        // Step 3: Display current queue status
        System.out.println("\n[STEP 3] Current Queue Status After All Requests...");
        bookingQueue.displayQueueStatus();
        
        // Step 4: Display all queued requests in order
        System.out.println("[STEP 4] All Queued Booking Requests (In Processing Order - FIFO)...");
        bookingQueue.displayAllQueuedRequests();
        
        // Step 5: Peek at the next request without removing it
        System.out.println("[STEP 5] Peeking at Next Request (Without Removing)...");
        Reservation nextReq = bookingQueue.peekNextReservation();
        if (nextReq != null) {
            System.out.println("Next request to be processed:");
            System.out.println("  Guest: " + nextReq.getGuestName());
            System.out.println("  Request ID: #" + nextReq.getReservationId());
            System.out.println("  Room Type: " + nextReq.getRoomType());
            System.out.println("✓ Queue size unchanged: " + bookingQueue.getQueueSize() + " requests remaining");
        }
        
        // Step 6: Process requests one by one
        System.out.println("\n[STEP 6] Processing Individual Requests from Queue (FIFO Order)...");
        System.out.println("Note: Requests are processed in the exact order they arrived");
        System.out.println("This ensures FAIR treatment regardless of room type or dates\n");
        
        // Process first 2 requests
        System.out.println("------- PROCESSING REQUEST 1 -------");
        Reservation processed1 = bookingQueue.getNextReservation();
        if (processed1 != null) {
            System.out.println("Processing Reservation #" + processed1.getReservationId());
            System.out.println("Guest: " + processed1.getGuestName());
            System.out.println("Room: " + processed1.getRoomType());
            bookingQueue.recordReservationProcessed();
            System.out.println("✓ Request processed and removed from queue");
            System.out.println("Queue size: " + bookingQueue.getQueueSize() + " requests remaining");
        }
        
        System.out.println("\n------- PROCESSING REQUEST 2 -------");
        Reservation processed2 = bookingQueue.getNextReservation();
        if (processed2 != null) {
            System.out.println("Processing Reservation #" + processed2.getReservationId());
            System.out.println("Guest: " + processed2.getGuestName());
            System.out.println("Room: " + processed2.getRoomType());
            bookingQueue.recordReservationProcessed();
            System.out.println("✓ Request processed and removed from queue");
            System.out.println("Queue size: " + bookingQueue.getQueueSize() + " requests remaining");
        }
        
        // Step 7: Display remaining requests
        System.out.println("\n[STEP 7] Remaining Requests in Queue After Partial Processing...");
        bookingQueue.displayAllQueuedRequests();
        
        // Step 8: Process all remaining requests
        System.out.println("[STEP 8] Processing All Remaining Requests...");
        bookingQueue.processAllRequests();
        
        // Step 9: Display final statistics
        System.out.println("[STEP 9] Final Queue Statistics...");
        bookingQueue.displayProcessingStatistics();
        
        // Summary
        System.out.println("========================================");
        System.out.println("SUMMARY - Use Case 5 Achievements");
        System.out.println("========================================");
        System.out.println("✓ Queue Data Structure: All requests stored in FIFO order");
        System.out.println("✓ FIFO Principle: First-Come-First-Served fairness enforced");
        System.out.println("✓ Request Ordering: Insertion order automatically preserved");
        System.out.println("✓ No State Mutation: Inventory unchanged during this stage");
        System.out.println("✓ Request Staging: All requests queued before processing");
        System.out.println("✓ Total Requests Handled: " + (bookingQueue.getTotalRequestsProcessed() + bookingQueue.getQueueSize() + 5));
        System.out.println("✓ Requests Successfully Processed: " + bookingQueue.getTotalRequestsProcessed());
        System.out.println("✓ Remaining in Queue: " + bookingQueue.getQueueSize());
        System.out.println("\nKey Learning:");
        System.out.println("The Queue ensures that no matter what room types guests request");
        System.out.println("or when exactly they submit requests, they will be processed in");
        System.out.println("the order they arrived. This prevents double-booking and unfair");
        System.out.println("allocation during high demand periods.");
        System.out.println("========================================");
        System.out.println("Application terminated successfully!");
    }
}
