/**
 * RoomNotAvailableException - Exception thrown when requested room type has no availability
 * 
 * This exception indicates that the guest requested a room type that exists
 * in the system but currently has no available rooms.
 * 
 * @author Hotel Booking Team
 * @version 9.0
 * @since 2026-04-01
 */
public class RoomNotAvailableException extends InvalidBookingException {
    
    private String roomType;
    private int requestedCount;
    private int availableCount;
    
    /**
     * Constructor - Creates exception with availability information
     * 
     * @param roomType The room type that's unavailable
     * @param requestedCount How many rooms were requested
     * @param availableCount How many are actually available
     */
    public RoomNotAvailableException(String roomType, int requestedCount, int availableCount) {
        super("No availability for " + roomType + ". Requested: " + requestedCount + 
              ", Available: " + availableCount);
        this.roomType = roomType;
        this.requestedCount = requestedCount;
        this.availableCount = availableCount;
    }
    
    /**
     * Get the room type that was unavailable
     * 
     * @return The room type
     */
    public String getRoomType() {
        return roomType;
    }
    
    /**
     * Get the number of rooms requested
     * 
     * @return Requested room count
     */
    public int getRequestedCount() {
        return requestedCount;
    }
    
    /**
     * Get the number of rooms available
     * 
     * @return Available room count
     */
    public int getAvailableCount() {
        return availableCount;
    }
}
