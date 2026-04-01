import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * RoomInventory Class - Centralized Room Inventory Management
 * 
 * This class manages the availability of different room types using a HashMap.
 * It serves as the single source of truth for all room availability data in the system.
 * 
 * Key Concepts:
 * - HashMap: Maps room types to available counts with O(1) average lookup time
 * - Single Source of Truth: All availability data centralized in one structure
 * - Encapsulation: Inventory operations are encapsulated and controlled
 * - Separation of Concerns: Inventory management separated from room domain model
 * - Scalability: Adding new room types requires only a HashMap entry
 * 
 * Previous Problem (Use Case 2):
 * Availability was scattered across independent static variables:
 *   - static int singleRoomAvailability
 *   - static int doubleRoomAvailability
 *   - static int suiteRoomAvailability
 * This approach was non-scalable and prone to inconsistencies.
 * 
 * Solution (Use Case 3):
 * HashMap<String, Integer> provides:
 *   - Centralized storage
 *   - O(1) access time
 *   - Easy scalability
 *   - Consistent state management
 * 
 * @author Hotel Booking Team
 * @version 3.0
 * @since 2026-04-01
 */
public class RoomInventory {
    
    // HashMap storing room type as key and availability count as value
    // This is the single source of truth for all room availability data
    private HashMap<String, Integer> inventoryMap;
    
    /**
     * Constructor - Initializes an empty inventory
     * Room types and their availability can be added using addRoom() method
     */
    public RoomInventory() {
        this.inventoryMap = new HashMap<>();
    }
    
    /**
     * Add or register a new room type to the inventory
     * This method allows registering room types and their initial availability count.
     * 
     * @param roomType The type of room (e.g., "Single Room", "Double Room", "Suite Room")
     * @param availableCount The initial number of available rooms
     * @throws IllegalArgumentException if roomType is null or availableCount is negative
     */
    public void addRoom(String roomType, int availableCount) {
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        if (availableCount < 0) {
            throw new IllegalArgumentException("Available count cannot be negative");
        }
        inventoryMap.put(roomType, availableCount);
    }
    
    /**
     * Get the current availability of a specific room type
     * 
     * @param roomType The type of room to check
     * @return The number of available rooms, or 0 if room type not found
     */
    public int getAvailability(String roomType) {
        return inventoryMap.getOrDefault(roomType, 0);
    }
    
    /**
     * Book a room - Decrements availability for the specified room type
     * This method is used when a booking is confirmed.
     * 
     * @param roomType The type of room to book
     * @return true if booking was successful, false if room type not found or no availability
     */
    public boolean bookRoom(String roomType) {
        if (!inventoryMap.containsKey(roomType)) {
            return false; // Room type does not exist
        }
        
        int currentAvailability = inventoryMap.get(roomType);
        if (currentAvailability <= 0) {
            return false; // No availability for this room type
        }
        
        // Decrement availability
        inventoryMap.put(roomType, currentAvailability - 1);
        return true;
    }
    
    /**
     * Cancel a booking - Increments availability for the specified room type
     * This method is used when a booking is cancelled.
     * 
     * @param roomType The type of room whose booking is being cancelled
     * @return true if cancellation was successful, false if room type not found
     */
    public boolean cancelBooking(String roomType) {
        if (!inventoryMap.containsKey(roomType)) {
            return false; // Room type does not exist
        }
        
        int currentAvailability = inventoryMap.get(roomType);
        inventoryMap.put(roomType, currentAvailability + 1);
        return true;
    }
    
    /**
     * Get all room types currently in the inventory
     * 
     * @return A set containing all room type keys
     */
    public Set<String> getAllRoomTypes() {
        return inventoryMap.keySet();
    }
    
    /**
     * Get the total number of available rooms across all types
     * 
     * @return The sum of all available rooms
     */
    public int getTotalAvailability() {
        int total = 0;
        for (int availability : inventoryMap.values()) {
            total += availability;
        }
        return total;
    }
    
    /**
     * Update availability for an existing room type
     * This method allows manual adjustment of availability counts.
     * 
     * @param roomType The type of room to update
     * @param newAvailability The new availability count
     * @return true if update was successful, false if room type not found
     * @throws IllegalArgumentException if newAvailability is negative
     */
    public boolean updateAvailability(String roomType, int newAvailability) {
        if (newAvailability < 0) {
            throw new IllegalArgumentException("Availability cannot be negative");
        }
        
        if (!inventoryMap.containsKey(roomType)) {
            return false; // Room type does not exist
        }
        
        inventoryMap.put(roomType, newAvailability);
        return true;
    }
    
    /**
     * Display the current inventory status
     * Shows all room types and their current availability
     */
    public void displayInventory() {
        System.out.println("\n========================================");
        System.out.println("CURRENT ROOM INVENTORY");
        System.out.println("========================================");
        
        if (inventoryMap.isEmpty()) {
            System.out.println("No rooms in inventory.");
        } else {
            for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
            }
            System.out.println("----------------------------------------");
            System.out.println("Total Available Rooms: " + getTotalAvailability());
        }
        System.out.println("========================================\n");
    }
}
