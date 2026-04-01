import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConcurrentBookingProcessor Class - Thread-safe booking request processing
 * 
 * This class processes booking requests from multiple threads in a thread-safe manner.
 * It uses synchronized methods and blocks to protect shared resources (inventory and queue)
 * from race conditions. Critical sections ensure that only one thread can perform
 * allocation and inventory updates at a time.
 * 
 * Key Concepts:
 * - Race Conditions: Multiple threads accessing shared data simultaneously
 * - Thread Safety: Synchronized access to shared resources prevents corruption
 * - Shared Mutable State: Booking queue and inventory are shared across threads
 * - Critical Sections: Synchronized blocks protect allocation operations
 * - Synchronized Access: Mutex/locks ensure exclusive access to critical sections
 * - Atomic Operations: Counters use AtomicInteger for thread-safe counting
 * 
 * Thread Safety Mechanisms:
 * 1. Synchronized methods: processBooking() is synchronized
 * 2. Synchronized blocks: Critical sections for inventory checks and updates
 * 3. Atomic variables: AtomicInteger for counters (no manual synchronization needed)
 * 4. Volatile fields: Flags shared across threads
 * 
 * Workflow:
 * 1. Thread removes booking request from queue
 * 2. CRITICAL SECTION: Check room availability
 * 3. CRITICAL SECTION: Allocate room and update inventory
 * 4. Update statistics
 * 5. Return result to requesting thread
 * 
 * Key Invariants:
 * - Inventory count never goes negative
 * - No room allocated twice (double allocation prevented)
 * - All threads see consistent inventory state
 * - Statistics accurately reflect all operations
 * 
 * @author Hotel Booking Team
 * @version 11.0
 * @since 2026-04-01
 */
public class ConcurrentBookingProcessor implements Runnable {
    
    // Shared resources protected by synchronization
    private RoomInventory inventory;
    private Queue<BookingRequest> bookingQueue;
    
    // Statistics tracked with AtomicInteger (thread-safe)
    private AtomicInteger successfulBookings;
    private AtomicInteger failedBookings;
    private AtomicInteger totalRequests;
    
    // Thread control
    private volatile boolean running;
    private static final Object QUEUE_LOCK = new Object();
    
    /**
     * Constructor - Initialize processor with shared resources
     * 
     * @param inventory Shared room inventory
     * @param bookingQueue Shared booking request queue
     */
    public ConcurrentBookingProcessor(RoomInventory inventory, Queue<BookingRequest> bookingQueue) {
        if (inventory == null || bookingQueue == null) {
            throw new IllegalArgumentException("Resources cannot be null");
        }
        this.inventory = inventory;
        this.bookingQueue = bookingQueue;
        this.successfulBookings = new AtomicInteger(0);
        this.failedBookings = new AtomicInteger(0);
        this.totalRequests = new AtomicInteger(0);
        this.running = true;
    }
    
    /**
     * Run method - Main thread execution loop
     * 
     * Continuously processes booking requests from the queue until
     * queue is empty for an extended period (or explicit stop signal received).
     * Each iteration performs synchronized access to shared resources.
     * Uses timeout mechanism to wait for requests rather than exiting immediately.
     */
    @Override
    public void run() {
        int emptyChecks = 0;
        final int MAX_EMPTY_CHECKS = 3; // Wait for a few cycles before exiting
        
        while (running) {
            BookingRequest request = null;
            
            // CRITICAL SECTION: Retrieve request from queue
            synchronized (QUEUE_LOCK) {
                if (bookingQueue.isEmpty()) {
                    emptyChecks++;
                    if (emptyChecks >= MAX_EMPTY_CHECKS) {
                        break; // Queue has been empty for multiple checks, exit
                    }
                } else {
                    emptyChecks = 0; // Reset counter when queue has items
                    request = bookingQueue.poll();
                }
            }
            
            if (request != null) {
                try {
                    // Process the booking request (synchronized method)
                    processBooking(request);
                } catch (Exception e) {
                    // Log error but continue processing
                    System.err.println("Error processing booking: " + e.getMessage());
                    failedBookings.incrementAndGet();
                }
            } else if (emptyChecks < MAX_EMPTY_CHECKS) {
                // Queue was empty but we haven't reached max checks, wait a bit
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    running = false;
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    /**
     * Process a single booking request - SYNCHRONIZED METHOD
     * 
     * This synchronized method ensures that only one thread can execute
     * the booking logic at a time. This prevents race conditions where
     * multiple threads could check availability and then both allocate
     * the same room.
     * 
     * CRITICAL SECTION Operations:
     * 1. Check room availability (reads shared state)
     * 2. Allocate room (modifies shared state)
     * 3. Update inventory (modifies shared state)
     * 
     * Thread Safety:
     * - Method is synchronized on 'this' (the processor object)
     * - Only one thread can execute this method at a time
     * - Other threads wait for lock release
     * - Prevents double allocation and inventory corruption
     * 
     * @param request The booking request to process
     */
    private synchronized void processBooking(BookingRequest request) {
        totalRequests.incrementAndGet();
        
        // Check if room type is available
        int availableCount = inventory.getAvailability(request.getRoomType());
        
        if (availableCount > 0) {
            // Room is available - allocate it
            try {
                inventory.bookRoom(request.getRoomType());
                request.setSuccessful(true);
                successfulBookings.incrementAndGet();
                
                // Log successful booking
                System.out.println("[" + Thread.currentThread().getName() + "] " +
                    "✓ Booked: " + request.getRoomType() + 
                    " (Guest: " + request.getGuestName() + 
                    ", Remaining: " + inventory.getAvailability(request.getRoomType()) + ")");
                
            } catch (Exception e) {
                request.setSuccessful(false);
                failedBookings.incrementAndGet();
                System.out.println("[" + Thread.currentThread().getName() + "] " +
                    "✗ Booking failed: " + e.getMessage());
            }
        } else {
            // No room available - booking fails
            request.setSuccessful(false);
            failedBookings.incrementAndGet();
            
            System.out.println("[" + Thread.currentThread().getName() + "] " +
                "✗ No availability: " + request.getRoomType() + 
                " (Guest: " + request.getGuestName() + ")");
        }
    }
    
    /**
     * Stop the processor thread
     */
    public void stop() {
        running = false;
    }
    
    /**
     * Get successful bookings count
     * 
     * @return Number of successful bookings
     */
    public int getSuccessfulBookings() {
        return successfulBookings.get();
    }
    
    /**
     * Get failed bookings count
     * 
     * @return Number of failed bookings
     */
    public int getFailedBookings() {
        return failedBookings.get();
    }
    
    /**
     * Get total requests processed
     * 
     * @return Total booking requests processed
     */
    public int getTotalRequests() {
        return totalRequests.get();
    }
    
    /**
     * BookingRequest - Encapsulates a booking request
     * 
     * Immutable data class representing a booking request from a guest.
     * Thread-safe for passing between threads.
     */
    public static class BookingRequest {
        private final String guestName;
        private final String roomType;
        private final long timestamp;
        private volatile boolean successful;
        
        public BookingRequest(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.timestamp = System.currentTimeMillis();
            this.successful = false;
        }
        
        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
        public long getTimestamp() { return timestamp; }
        public boolean isSuccessful() { return successful; }
        public void setSuccessful(boolean successful) { this.successful = successful; }
        
        @Override
        public String toString() {
            return guestName + "->" + roomType + "(" + (successful ? "OK" : "FAIL") + ")";
        }
    }
}
