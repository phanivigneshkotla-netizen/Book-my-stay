import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 * AllocationService Class - Room Allocation and Booking Confirmation
 * 
 * This class handles the allocation of rooms to confirmed reservations.
 * It uses a Set data structure to enforce uniqueness of room assignments,
 * preventing the critical problem of double-booking. Each room can only be
 * assigned once, guaranteed by the Set's uniqueness property.
 * 
 * Key Concepts:
 * - Problem of Double Booking: Without controlled allocation, multiple guests
 *   could be assigned the same room, causing conflicts and inconsistency
 * - Set Data Structure: Set<String> stores room IDs and enforces uniqueness
 *   by design. Any attempt to add a duplicate room ID will fail, preventing reuse
 * - Uniqueness Enforcement: Checking against the set guarantees no room is
 *   assigned twice, eliminating manual duplicate checks
 * - Mapping: HashMap<String, Set<String>> groups allocated rooms by type
 * - Atomic Operations: Room assignment and inventory update occur together
 * - Inventory Synchronization: Availability is updated immediately after allocation
 * 
 * Room ID Format: "ROOMTYPE_SEQUENCE"
 * Examples: "SINGLE_001", "DOUBLE_005", "SUITE_002"
 * 
 * Flow (Safe Allocation):
 * 1. Check if room type has availability (read from RoomInventory)
 * 2. Check if a room ID would be unique (check against Set)
 * 3. Generate new room ID
 * 4. Add room ID to Set (enforces uniqueness)
 * 5. Decrement inventory for that room type
 * 6. Return confirmation with room ID
 * 
 * Double-Booking Prevention:
 * - Set prevents accidental reuse of room IDs
 * - Inventory check ensures room type exists
 * - Atomic operations maintain consistency
 * - No race conditions in sequential processing
 * 
 * @author Hotel Booking Team
 * @version 6.0
 * @since 2026-04-01
 */
public class AllocationService {
    
    // Reference to inventory for availability checks
    private RoomInventory inventory;
    
    // HashMap mapping room type to Set of allocated room IDs
    // Example: "Single Room" -> {"SINGLE_001", "SINGLE_002", "SINGLE_003"}
    // This structure groups allocated rooms by type and enforces uniqueness
    private HashMap<String, Set<String>> allocatedRooms;
    
    // Counter for generating unique room sequences per type
    // Example: "SINGLE" -> 5 means "SINGLE_005" is the last assigned ID
    private HashMap<String, Integer> roomIdCounters;
    
    // Track successful allocations
    private int totalAllocationsSuccessful;
    private int totalAllocationsFailed;
    
    /**
     * Constructor - Initializes AllocationService with inventory reference
     * 
     * @param inventory The centralized room inventory
     */
    public AllocationService(RoomInventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null");
        }
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.roomIdCounters = new HashMap<>();
        this.totalAllocationsSuccessful = 0;
        this.totalAllocationsFailed = 0;
    }
    
    /**
     * Initialize a room type for allocation
     * This sets up the necessary data structures for tracking allocated rooms.
     * 
     * @param roomType The room type to initialize
     */
    public void initializeRoomType(String roomType) {
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        if (!allocatedRooms.containsKey(roomType)) {
            allocatedRooms.put(roomType, new HashSet<>());
            roomIdCounters.put(roomType, 0);
        }
    }
    
    /**
     * Generate a unique room ID for a room type
     * Format: ROOMTYPE_SEQUENCE (e.g., "SINGLE_001")
     * 
     * @param roomType The room type
     * @return A unique room ID
     */
    private String generateRoomId(String roomType) {
        int counter = roomIdCounters.getOrDefault(roomType, 0) + 1;
        roomIdCounters.put(roomType, counter);
        
        // Create room type prefix (e.g., "Single Room" -> "SINGLE")
        String prefix = roomType.replaceAll(" ", "_").toUpperCase();
        
        // Format: "SINGLE_001", "SINGLE_002", etc.
        return String.format("%s_%03d", prefix, counter);
    }
    
    /**
     * Allocate a room to a reservation (Core Allocation Logic)
     * This is the critical method that prevents double-booking.
     * 
     * Process:
     * 1. Check if room type has availability in inventory
     * 2. Initialize room type if not already done (safety check)
     * 3. Generate a new unique room ID
     * 4. Verify room ID is not already allocated (Set uniqueness check)
     * 5. Add room ID to Set (enforces uniqueness, prevents reuse)
     * 6. Decrement inventory atomically
     * 7. Record successful allocation
     * 
     * @param reservation The reservation requesting a room
     * @return An AllocationResult indicating success/failure and room details
     */
    public AllocationResult allocateRoom(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        
        String roomType = reservation.getRoomType();
        String guestName = reservation.getGuestName();
        int reservationId = reservation.getReservationId();
        
        // Step 1: Check availability in inventory
        int availableCount = inventory.getAvailability(roomType);
        if (availableCount <= 0) {
            String message = "No availability for " + roomType;
            totalAllocationsFailed++;
            return new AllocationResult(false, null, message, reservationId);
        }
        
        // Step 2: Initialize room type if needed
        if (!allocatedRooms.containsKey(roomType)) {
            initializeRoomType(roomType);
        }
        
        // Step 3: Generate unique room ID
        String roomId = generateRoomId(roomType);
        
        // Step 4 & 5: Verify uniqueness and add to Set
        // This is where we prevent double-booking
        Set<String> roomsOfType = allocatedRooms.get(roomType);
        if (roomsOfType.contains(roomId)) {
            // This should never happen with proper ID generation, but we check anyway
            String message = "Room ID collision detected: " + roomId;
            totalAllocationsFailed++;
            return new AllocationResult(false, null, message, reservationId);
        }
        
        // Add to Set - this enforces uniqueness for future checks
        roomsOfType.add(roomId);
        
        // Step 6: Update inventory atomically
        // This ensures availability reflects actual allocations
        boolean inventoryUpdated = inventory.bookRoom(roomType);
        
        if (!inventoryUpdated) {
            // Rollback: Remove from set if inventory update fails
            roomsOfType.remove(roomId);
            String message = "Inventory update failed for " + roomType;
            totalAllocationsFailed++;
            return new AllocationResult(false, null, message, reservationId);
        }
        
        // Step 7: Record success
        totalAllocationsSuccessful++;
        String message = "Room allocated successfully";
        return new AllocationResult(true, roomId, message, reservationId);
    }
    
    /**
     * Check if a room ID has been allocated
     * Uses Set membership check for O(1) uniqueness verification
     * 
     * @param roomType The room type
     * @param roomId The room ID to check
     * @return true if room ID is already allocated, false otherwise
     */
    public boolean isRoomIdAllocated(String roomType, String roomId) {
        if (!allocatedRooms.containsKey(roomType)) {
            return false;
        }
        return allocatedRooms.get(roomType).contains(roomId);
    }
    
    /**
     * Get all allocated room IDs for a room type
     * 
     * @param roomType The room type
     * @return A set of allocated room IDs, or empty set if none
     */
    public Set<String> getAllocatedRoomIds(String roomType) {
        if (!allocatedRooms.containsKey(roomType)) {
            return new HashSet<>();
        }
        // Return copy to prevent external modification
        return new HashSet<>(allocatedRooms.get(roomType));
    }
    
    /**
     * Get total number of allocated rooms
     * 
     * @return Total count of allocated rooms across all types
     */
    public int getTotalAllocatedRooms() {
        int total = 0;
        for (Set<String> roomIds : allocatedRooms.values()) {
            total += roomIds.size();
        }
        return total;
    }
    
    /**
     * Get statistics on allocation success/failure
     * 
     * @return Object containing allocation statistics
     */
    public AllocationStatistics getStatistics() {
        return new AllocationStatistics(
            totalAllocationsSuccessful,
            totalAllocationsFailed,
            getTotalAllocatedRooms()
        );
    }
    
    /**
     * Display allocation report showing all allocated rooms
     */
    public void displayAllocationReport() {
        System.out.println("\n========================================");
        System.out.println("ROOM ALLOCATION REPORT");
        System.out.println("========================================");
        
        for (String roomType : allocatedRooms.keySet()) {
            Set<String> roomIds = allocatedRooms.get(roomType);
            System.out.println("\n" + roomType);
            System.out.println("-".repeat(40));
            
            if (roomIds.isEmpty()) {
                System.out.println("No rooms allocated");
            } else {
                System.out.println("Allocated Rooms (" + roomIds.size() + "):");
                int count = 0;
                for (String roomId : roomIds) {
                    count++;
                    System.out.println("  " + count + ". " + roomId);
                }
            }
        }
        
        System.out.println("\n========================================");
        System.out.println("Total Allocated: " + getTotalAllocatedRooms());
        System.out.println("Successful Allocations: " + totalAllocationsSuccessful);
        System.out.println("Failed Allocations: " + totalAllocationsFailed);
        System.out.println("========================================\n");
    }
    
    /**
     * Inner class representing allocation result
     */
    public static class AllocationResult {
        private boolean success;
        private String roomId;
        private String message;
        private int reservationId;
        
        public AllocationResult(boolean success, String roomId, String message, int reservationId) {
            this.success = success;
            this.roomId = roomId;
            this.message = message;
            this.reservationId = reservationId;
        }
        
        public boolean isSuccess() { return success; }
        public String getRoomId() { return roomId; }
        public String getMessage() { return message; }
        public int getReservationId() { return reservationId; }
    }
    
    /**
     * Inner class for allocation statistics
     */
    public static class AllocationStatistics {
        private int successful;
        private int failed;
        private int totalAllocated;
        
        public AllocationStatistics(int successful, int failed, int totalAllocated) {
            this.successful = successful;
            this.failed = failed;
            this.totalAllocated = totalAllocated;
        }
        
        public int getSuccessful() { return successful; }
        public int getFailed() { return failed; }
        public int getTotalAllocated() { return totalAllocated; }
    }
}
