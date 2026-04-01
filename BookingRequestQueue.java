import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * BookingRequestQueue Class - Manages incoming booking requests using FIFO ordering
 * 
 * This class uses the Queue data structure to manage booking requests in the order
 * they arrive. It implements the FIFO (First-Come-First-Served) principle, ensuring
 * fair treatment of all booking requests.
 * 
 * Key Concepts:
 * - Queue Data Structure: Natural fit for request management (FIFO)
 * - FIFO Principle: First request in is first request out
 * - Fairness: Requesting guests are served in arrival order
 * - Request Ordering: Queue preserves insertion order automatically
 * - No State Mutation: Queueing does not modify inventory
 * - Request Staging: Requests are collected before processing
 * 
 * Queue Operations:
 * - enqueue/add(): Add a new reservation to the queue
 * - dequeue/remove(): Retrieve the next reservation from the queue
 * - peek(): View the next request without removing it
 * - size(): Check queue length
 * - isEmpty(): Check if queue is empty
 * 
 * Advantages over Array/ArrayList:
 * - Natural FIFO ordering
 * - Efficient add and remove operations
 * - Semantically clear for request management
 * - Prevents accidental random access to requests
 * 
 * @author Hotel Booking Team
 * @version 5.0
 * @since 2026-04-01
 */
public class BookingRequestQueue {
    
    // Queue data structure for managing reservations in FIFO order
    // LinkedList is used as the underlying implementation of the Queue interface
    private Queue<Reservation> reservationQueue;
    
    // Track total requests processed for statistics
    private int totalRequestsProcessed;
    
    /**
     * Constructor - Initializes an empty booking request queue
     * Uses LinkedList implementation for efficient queue operations
     */
    public BookingRequestQueue() {
        this.reservationQueue = new LinkedList<>();
        this.totalRequestsProcessed = 0;
    }
    
    /**
     * Add a new reservation to the queue (enqueue operation)
     * Requests are added to the end of the queue and will be processed
     * in the order they were received.
     * 
     * @param reservation The reservation to add to the queue
     * @return true if the reservation was successfully added
     * @throws IllegalArgumentException if reservation is null
     */
    public boolean addReservationRequest(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        return reservationQueue.add(reservation);
    }
    
    /**
     * Get the next reservation to be processed (dequeue operation)
     * Retrieves and removes the reservation that arrived first (FIFO).
     * No inventory is modified at this stage - only request management.
     * 
     * @return The next reservation in queue, or null if queue is empty
     */
    public Reservation getNextReservation() {
        return reservationQueue.poll();
    }
    
    /**
     * Peek at the next reservation without removing it
     * Allows inspection of upcoming requests without modifying queue state.
     * 
     * @return The next reservation in queue, or null if queue is empty
     */
    public Reservation peekNextReservation() {
        return reservationQueue.peek();
    }
    
    /**
     * Get the current size of the booking request queue
     * 
     * @return Number of reservations waiting to be processed
     */
    public int getQueueSize() {
        return reservationQueue.size();
    }
    
    /**
     * Check if the booking request queue is empty
     * 
     * @return true if no reservations are pending, false otherwise
     */
    public boolean isQueueEmpty() {
        return reservationQueue.isEmpty();
    }
    
    /**
     * Get the total number of requests processed so far
     * 
     * @return Count of reservations that have been dequeued
     */
    public int getTotalRequestsProcessed() {
        return totalRequestsProcessed;
    }
    
    /**
     * Record that a reservation has been processed
     * This is called when a reservation is fully processed (allocated a room).
     */
    public void recordReservationProcessed() {
        totalRequestsProcessed++;
    }
    
    /**
     * Display the current queue status
     */
    public void displayQueueStatus() {
        System.out.println("\n========================================");
        System.out.println("BOOKING REQUEST QUEUE STATUS");
        System.out.println("========================================");
        System.out.println("Requests in Queue: " + getQueueSize());
        System.out.println("Total Processed: " + getTotalRequestsProcessed());
        System.out.println("========================================\n");
    }
    
    /**
     * Display all reservations currently in the queue (in order)
     * This shows the order in which requests will be processed.
     */
    public void displayAllQueuedRequests() {
        System.out.println("\n========================================");
        System.out.println("QUEUED BOOKING REQUESTS (In Processing Order)");
        System.out.println("========================================");
        
        if (reservationQueue.isEmpty()) {
            System.out.println("Queue is empty. No pending requests.");
        } else {
            int position = 1;
            // Create iterator to display queue without modifying it
            Iterator<Reservation> iterator = reservationQueue.iterator();
            while (iterator.hasNext()) {
                Reservation res = iterator.next();
                System.out.println("\nPosition #" + position + " (Request ID: #" + res.getReservationId() + ")");
                System.out.println("-".repeat(40));
                System.out.println("Guest: " + res.getGuestName());
                System.out.println("Room Type: " + res.getRoomType());
                System.out.println("Check-in: " + res.getCheckInDate() + " → Check-out: " + res.getCheckOutDate());
                System.out.println("Guests: " + res.getNumberOfGuests());
                System.out.println("Request Time: " + res.getRequestTime());
                position++;
            }
        }
        System.out.println("\n========================================\n");
    }
    
    /**
     * Process and remove all requests from the queue
     * Displays each request as it's processed in FIFO order.
     */
    public void processAllRequests() {
        System.out.println("\n========================================");
        System.out.println("PROCESSING ALL BOOKING REQUESTS (FIFO ORDER)");
        System.out.println("========================================");
        
        int processCount = 0;
        while (!reservationQueue.isEmpty()) {
            Reservation res = reservationQueue.poll();
            processCount++;
            recordReservationProcessed();
            
            System.out.println("\n[Process #" + processCount + "] Processing Reservation #" + res.getReservationId());
            System.out.println("-".repeat(40));
            System.out.println("Guest: " + res.getGuestName());
            System.out.println("Room Type: " + res.getRoomType());
            System.out.println("Dates: " + res.getCheckInDate() + " → " + res.getCheckOutDate());
            System.out.println("Status: ✓ Processed (Ready for allocation)");
        }
        
        System.out.println("\n========================================");
        System.out.println("All " + processCount + " requests have been processed.");
        System.out.println("========================================\n");
    }
    
    /**
     * Display the history of processed requests
     * This shows how many requests have been handled overall.
     */
    public void displayProcessingStatistics() {
        System.out.println("\n========================================");
        System.out.println("REQUEST PROCESSING STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Requests in Queue: " + getQueueSize());
        System.out.println("Total Requests Processed: " + getTotalRequestsProcessed());
        System.out.println("Total Requests Handled: " + (getQueueSize() + getTotalRequestsProcessed()));
        System.out.println("========================================\n");
    }
}
