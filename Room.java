/**
 * Abstract Room Class - Represents the generalized concept of a hotel room
 * 
 * This abstract class serves as the base for all room types in the Hotel Booking System.
 * It defines common attributes and behavior that all room types share while enforcing
 * a consistent structure through abstract methods.
 * 
 * Key Concepts:
 * - Abstract Class: Cannot be instantiated directly; serves as a blueprint for subclasses
 * - Encapsulation: Room attributes are protected and controlled through the class interface
 * - Inheritance: Concrete room classes extend this class to specialize room behavior
 * 
 * @author Hotel Booking Team
 * @version 2.0
 * @since 2026-04-01
 */
public abstract class Room {
    
    // Protected attributes accessible to subclasses
    protected String roomType;
    protected int numberOfBeds;
    protected double roomSize;  // in square meters
    protected double pricePerNight;
    protected String roomDescription;
    
    /**
     * Constructor for Room class
     * 
     * @param roomType The type of room (e.g., Single, Double, Suite)
     * @param numberOfBeds Number of beds in the room
     * @param roomSize Size of the room in square meters
     * @param pricePerNight Price per night for this room type
     */
    public Room(String roomType, int numberOfBeds, double roomSize, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
        this.roomDescription = "";
    }
    
    /**
     * Abstract method to get room details
     * Each subclass must implement this method to provide specific details
     * 
     * @return A detailed description of the room
     */
    public abstract String getRoomDetails();
    
    /**
     * Get the room type
     * 
     * @return The type of room
     */
    public String getRoomType() {
        return roomType;
    }
    
    /**
     * Get the number of beds
     * 
     * @return Number of beds in the room
     */
    public int getNumberOfBeds() {
        return numberOfBeds;
    }
    
    /**
     * Get the room size
     * 
     * @return Room size in square meters
     */
    public double getRoomSize() {
        return roomSize;
    }
    
    /**
     * Get the price per night
     * 
     * @return Price per night for this room
     */
    public double getPricePerNight() {
        return pricePerNight;
    }
    
    /**
     * Display room information to console
     * This method provides a standard way to display room details
     */
    public void displayRoomInfo() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Room Size: " + roomSize + " sq.m");
        System.out.println("Price per Night: $" + pricePerNight);
        System.out.println(getRoomDetails());
    }
}
