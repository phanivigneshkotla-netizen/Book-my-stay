/**
 * SingleRoom Class - Concrete implementation of a single room
 * 
 * This class represents a single room in the hotel. It extends the abstract Room class
 * and provides specific implementation details for single rooms.
 * 
 * Key Concepts:
 * - Inheritance: Extends the Room abstract class
 * - Polymorphism: Implements the abstract method getRoomDetails()
 * - Specialization: Provides specific behavior for single rooms
 * 
 * @author Hotel Booking Team
 * @version 2.0
 * @since 2026-04-01
 */
public class SingleRoom extends Room {
    
    private String bedType;
    
    /**
     * Constructor for SingleRoom class
     * Creates a single room with a specific bed type
     * 
     * @param bedType The type of bed (e.g., Queen, Twin)
     */
    public SingleRoom(String bedType) {
        super("Single Room", 1, 25.0, 50.0);
        this.bedType = bedType;
    }
    
    /**
     * Get room details specific to single rooms
     * 
     * @return A detailed description of the single room
     */
    @Override
    public String getRoomDetails() {
        return "Description: Cozy and comfortable room with " + bedType + 
               " bed. Perfect for solo travelers.\n" +
               "         Amenities: WiFi, Air Conditioning, Private Bathroom";
    }
    
    /**
     * Get the bed type
     * 
     * @return The type of bed in the room
     */
    public String getBedType() {
        return bedType;
    }
}
