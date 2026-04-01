/**
 * SuiteRoom Class - Concrete implementation of a suite room
 * 
 * This class represents a luxury suite room in the hotel. It extends the abstract Room class
 * and provides specific implementation details for suite rooms with premium amenities.
 * 
 * Key Concepts:
 * - Inheritance: Extends the Room abstract class
 * - Polymorphism: Implements the abstract method getRoomDetails()
 * - Specialization: Provides specific behavior for suite rooms
 * 
 * @author Hotel Booking Team
 * @version 2.0
 * @since 2026-04-01
 */
public class SuiteRoom extends Room {
    
    private int numberOfRooms;
    
    /**
     * Constructor for SuiteRoom class
     * Creates a suite room with multiple rooms
     * 
     * @param numberOfRooms The number of separate rooms in the suite
     */
    public SuiteRoom(int numberOfRooms) {
        super("Suite Room", 3, 80.0, 150.0);
        this.numberOfRooms = numberOfRooms;
    }
    
    /**
     * Get room details specific to suite rooms
     * 
     * @return A detailed description of the suite room
     */
    @Override
    public String getRoomDetails() {
        return "Description: Luxury suite with " + numberOfRooms + 
               " separate rooms. Premium choice for families or business travelers.\n" +
               "         Amenities: WiFi, Air Conditioning, Premium Bathroom with Jacuzzi, " +
               "Kitchenette, Living Area";
    }
    
    /**
     * Get the number of rooms in the suite
     * 
     * @return The number of rooms
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }
}
