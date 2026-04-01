/**
 * DoubleRoom Class - Concrete implementation of a double room
 * 
 * This class represents a double room in the hotel. It extends the abstract Room class
 * and provides specific implementation details for double rooms suitable for couples or
 * small families.
 * 
 * Key Concepts:
 * - Inheritance: Extends the Room abstract class
 * - Polymorphism: Implements the abstract method getRoomDetails()
 * - Specialization: Provides specific behavior for double rooms
 * 
 * @author Hotel Booking Team
 * @version 2.0
 * @since 2026-04-01
 */
public class DoubleRoom extends Room {
    
    private String roomView;
    
    /**
     * Constructor for DoubleRoom class
     * Creates a double room with a specific view
     * 
     * @param roomView The view from the room (e.g., City, Garden, Ocean)
     */
    public DoubleRoom(String roomView) {
        super("Double Room", 2, 35.0, 80.0);
        this.roomView = roomView;
    }
    
    /**
     * Get room details specific to double rooms
     * 
     * @return A detailed description of the double room
     */
    @Override
    public String getRoomDetails() {
        return "Description: Spacious room with King-sized bed and " + roomView + 
               " view. Ideal for couples.\n" +
               "         Amenities: WiFi, Air Conditioning, Private Bathroom, Balcony";
    }
    
    /**
     * Get the room view
     * 
     * @return The view from the room
     */
    public String getRoomView() {
        return roomView;
    }
}
