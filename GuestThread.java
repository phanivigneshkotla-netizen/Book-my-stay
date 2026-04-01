import java.util.Queue;
import java.util.Random;

/**
 * GuestThread Class - Represents a guest making concurrent booking requests
 * 
 * This class implements the Runnable interface and represents a single guest
 * making multiple booking requests concurrently with other guests. Each guest
 * thread adds its requests to a shared queue where they are processed by
 * ConcurrentBookingProcessor threads.
 * 
 * Key Concepts:
 * - Thread represents a single guest
 * - Concurrent execution with other guest threads
 * - Shared queue for inter-thread communication
 * - Simulates real-world concurrent booking behavior
 * - Multiple threads generating requests simultaneously
 * 
 * Behavior:
 * 1. Generate booking requests for various room types
 * 2. Add requests to shared queue (thread-safe LinkedList poll/offer)
 * 3. Simulate booking delays (realistic timing)
 * 4. Run concurrently with other guest threads
 * 5. Demonstrate race conditions and synchronization impact
 * 
 * Thread Safety:
 * - Uses synchronized methods when accessing shared queue
 * - No shared state local to this thread
 * - Queue operations are atomic (offer/poll)
 * - Multiple guests can run concurrently
 * 
 * @author Hotel Booking Team
 * @version 11.0
 * @since 2026-04-01
 */
public class GuestThread implements Runnable {
    
    // Guest identification
    private String guestId;
    private String guestName;
    
    // Shared resources
    private Queue<ConcurrentBookingProcessor.BookingRequest> bookingQueue;
    
    // Guest request configuration
    private int requestCount;
    private String[] availableRoomTypes;
    private Random random;
    
    // Statistics
    private int requestsSubmitted;
    
    /**
     * Constructor - Initialize guest thread
     * 
     * @param guestId Unique guest identifier
     * @param guestName Guest's name
     * @param bookingQueue Shared booking request queue
     * @param requestCount Number of booking requests this guest will make
     * @param roomTypes Available room types guest can request
     */
    public GuestThread(String guestId, String guestName, 
                       Queue<ConcurrentBookingProcessor.BookingRequest> bookingQueue,
                       int requestCount, String[] roomTypes) {
        if (guestId == null || guestName == null || bookingQueue == null || roomTypes == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.guestId = guestId;
        this.guestName = guestName;
        this.bookingQueue = bookingQueue;
        this.requestCount = requestCount;
        this.availableRoomTypes = roomTypes;
        this.random = new Random();
        this.requestsSubmitted = 0;
    }
    
    /**
     * Run method - Main thread execution
     * 
     * This is the entry point when the thread starts. Each guest generates
     * multiple booking requests and adds them to the shared queue.
     * The thread sleeps briefly between requests to simulate realistic behavior
     * and allow other threads to run concurrently.
     */
    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] " +
            "Guest " + guestId + " (" + guestName + ") starting " + 
            requestCount + " booking requests");
        
        // Generate booking requests
        for (int i = 0; i < requestCount; i++) {
            try {
                // Generate random room type request
                String roomType = availableRoomTypes[random.nextInt(availableRoomTypes.length)];
                
                // Create booking request
                ConcurrentBookingProcessor.BookingRequest request = 
                    new ConcurrentBookingProcessor.BookingRequest(guestName, roomType);
                
                // Add to shared queue (thread-safe)
                // Multiple threads may be adding to queue simultaneously
                synchronized (bookingQueue) {
                    bookingQueue.offer(request);
                    requestsSubmitted++;
                }
                
                // Small delay to simulate realistic user behavior
                // This allows other threads to interleave
                Thread.sleep(10 + random.nextInt(20));
                
            } catch (InterruptedException e) {
                System.err.println("[" + Thread.currentThread().getName() + "] " +
                    "Guest interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("[" + Thread.currentThread().getName() + "] " +
            "Guest " + guestId + " completed - " + requestsSubmitted + " requests submitted");
    }
    
    /**
     * Get number of requests submitted by this guest
     * 
     * @return Requests submitted count
     */
    public int getRequestsSubmitted() {
        return requestsSubmitted;
    }
    
    /**
     * Get guest ID
     * 
     * @return Guest identifier
     */
    public String getGuestId() {
        return guestId;
    }
    
    /**
     * Get guest name
     * 
     * @return Guest name
     */
    public String getGuestName() {
        return guestName;
    }
}
