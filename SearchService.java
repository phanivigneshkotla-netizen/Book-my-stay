import java.util.HashMap;
import java.util.Map;

/**
 * SearchService Class - Room Search and Availability Check Service
 * 
 * This class provides read-only search functionality for guests to view available rooms
 * and their details without modifying system state. It enforces a clear separation between
 * read-only operations (searches) and write operations (bookings).
 * 
 * Key Concepts:
 * - Read-Only Access: Search operations only read data; no modifications occur
 * - Defensive Programming: Validates data and checks before displaying results
 * - Separation of Concerns: Search logic isolated from booking and inventory mutation
 * - Inventory as State Holder: Accesses inventory only to check availability
 * - Domain Model Usage: Uses Room objects for descriptive information
 * - Validation Logic: Filters out unavailable room types from results
 * 
 * Interaction Pattern:
 * - Guest initiates search request
 * - SearchService queries RoomInventory for availability (read-only)
 * - SearchService retrieves Room details from room objects
 * - Only available rooms are displayed
 * - Inventory remains unchanged
 * 
 * Benefits:
 * - Accurate availability visibility without state mutation
 * - Reduced risk of accidental inventory corruption
 * - Clear boundary between search and booking operations
 * - Enables multiple concurrent searches without conflicts
 * 
 * @author Hotel Booking Team
 * @version 4.0
 * @since 2026-04-01
 */
public class SearchService {
    
    // Reference to the centralized inventory (read-only access)
    private RoomInventory inventory;
    
    // HashMap to store available room types and their objects
    // This is populated during search but does not modify the inventory
    private HashMap<String, Room> availableRooms;
    
    /**
     * Constructor - Initializes SearchService with access to inventory
     * 
     * @param inventory The centralized room inventory to query
     */
    public SearchService(RoomInventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null");
        }
        this.inventory = inventory;
        this.availableRooms = new HashMap<>();
    }
    
    /**
     * Register a room type with its corresponding Room object
     * This allows the search service to access room details during search operations.
     * Note: This does not modify inventory; it only stores the room object reference.
     * 
     * @param roomType The type of room (e.g., "Single Room")
     * @param room The Room object containing room details
     */
    public void registerRoomType(String roomType, Room room) {
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room object cannot be null");
        }
        availableRooms.put(roomType, room);
    }
    
    /**
     * Search for available rooms - Read-only operation
     * This method retrieves available room types from inventory and displays their details.
     * Inventory state is never modified during search.
     * 
     * @return A HashMap of available room types and their Room objects
     */
    public HashMap<String, Room> searchAvailableRooms() {
        HashMap<String, Room> searchResults = new HashMap<>();
        
        // Iterate through all registered room types
        for (String roomType : availableRooms.keySet()) {
            // Defensive check: Get availability from inventory (read-only access)
            int availability = inventory.getAvailability(roomType);
            
            // Validation logic: Include only rooms with availability > 0
            if (availability > 0) {
                searchResults.put(roomType, availableRooms.get(roomType));
            }
        }
        
        return searchResults;
    }
    
    /**
     * Get availability for a specific room type - Read-only operation
     * 
     * @param roomType The type of room to check
     * @return The number of available rooms, or -1 if room type not found
     */
    public int getAvailability(String roomType) {
        if (!availableRooms.containsKey(roomType)) {
            return -1; // Room type not registered
        }
        return inventory.getAvailability(roomType);
    }
    
    /**
     * Check if a specific room type is available - Read-only operation
     * 
     * @param roomType The type of room to check
     * @return true if the room type has at least one available room, false otherwise
     */
    public boolean isRoomAvailable(String roomType) {
        if (!availableRooms.containsKey(roomType)) {
            return false; // Room type not registered
        }
        return inventory.getAvailability(roomType) > 0;
    }
    
    /**
     * Get detailed information about a room type - Read-only operation
     * 
     * @param roomType The type of room to get details for
     * @return The Room object if available, null otherwise
     */
    public Room getRoomDetails(String roomType) {
        return availableRooms.getOrDefault(roomType, null);
    }
    
    /**
     * Display search results for available rooms - Read-only operation
     * Shows all rooms with availability > 0 along with their details and pricing.
     */
    public void displaySearchResults() {
        HashMap<String, Room> results = searchAvailableRooms();
        
        System.out.println("\n========================================");
        System.out.println("AVAILABLE ROOMS - SEARCH RESULTS");
        System.out.println("========================================");
        
        if (results.isEmpty()) {
            System.out.println("No rooms currently available. Please try again later.");
        } else {
            for (String roomType : results.keySet()) {
                Room room = results.get(roomType);
                int availability = inventory.getAvailability(roomType);
                
                System.out.println("\n" + roomType);
                System.out.println("-".repeat(40));
                room.displayRoomInfo();
                System.out.println("Available Rooms: " + availability);
            }
        }
        System.out.println("========================================\n");
    }
    
    /**
     * Display availability summary for all searched rooms - Read-only operation
     * Shows a quick overview of current availability status.
     */
    public void displayAvailabilitySummary() {
        System.out.println("\n========================================");
        System.out.println("AVAILABILITY SUMMARY");
        System.out.println("========================================");
        
        for (String roomType : availableRooms.keySet()) {
            int availability = inventory.getAvailability(roomType);
            String status = availability > 0 ? "AVAILABLE (" + availability + ")" : "NOT AVAILABLE";
            System.out.println(roomType + ": " + status);
        }
        System.out.println("========================================\n");
    }
    
    /**
     * Display all room types and their current details - Read-only operation
     * Shows complete information about each room type.
     */
    public void displayAllRoomDetails() {
        System.out.println("\n========================================");
        System.out.println("ALL ROOM TYPES - DETAILED INFORMATION");
        System.out.println("========================================");
        
        for (String roomType : availableRooms.keySet()) {
            Room room = availableRooms.get(roomType);
            int availability = inventory.getAvailability(roomType);
            
            System.out.println("\n" + roomType);
            System.out.println("-".repeat(40));
            System.out.println("Number of Beds: " + room.getNumberOfBeds());
            System.out.println("Room Size: " + room.getRoomSize() + " sq.m");
            System.out.println("Price per Night: $" + room.getPricePerNight());
            System.out.println("Availability: " + availability);
            System.out.println(room.getRoomDetails());
        }
        System.out.println("\n========================================\n");
    }
}
