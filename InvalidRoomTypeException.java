/**
 * InvalidRoomTypeException - Exception thrown for invalid room types
 * 
 * This exception indicates that a guest requested a room type that doesn't
 * exist in the hotel's inventory system.
 * 
 * @author Hotel Booking Team
 * @version 9.0
 * @since 2026-04-01
 */
public class InvalidRoomTypeException extends InvalidBookingException {
    
    private String attemptedRoomType;
    
    /**
     * Constructor - Creates exception with room type information
     * 
     * @param roomType The invalid room type that was requested
     */
    public InvalidRoomTypeException(String roomType) {
        super("Invalid room type: '" + roomType + "' does not exist in hotel inventory");
        this.attemptedRoomType = roomType;
    }
    
    /**
     * Get the invalid room type that triggered this exception
     * 
     * @return The room type that was invalid
     */
    public String getAttemptedRoomType() {
        return attemptedRoomType;
    }
}
