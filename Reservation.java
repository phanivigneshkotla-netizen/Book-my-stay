import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Reservation Class - Represents a guest's booking request
 * 
 * This class encapsulates all information related to a single booking request.
 * Each reservation represents a guest's intent to book a room without modifying
 * the inventory. The reservation is queued for processing.
 * 
 * Key Concepts:
 * - Immutability: Once created, a reservation's details do not change
 * - Encapsulation: All reservation data is contained and protected
 * - Timestamping: Request time is captured for fairness and ordering
 * - Unique Identification: Each reservation has a unique ID for tracking
 * 
 * @author Hotel Booking Team
 * @version 5.0
 * @since 2026-04-01
 */
public class Reservation {
    
    // Static counter for generating unique reservation IDs
    private static int reservationCounter = 1000;
    
    // Reservation attributes
    private int reservationId;
    private String guestName;
    private String roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime requestTime;
    private String contactEmail;
    private int numberOfGuests;
    
    /**
     * Constructor - Creates a new reservation with booking details
     * 
     * @param guestName The name of the guest making the reservation
     * @param roomType The type of room being requested
     * @param checkInDate The check-in date
     * @param checkOutDate The check-out date
     * @param contactEmail The guest's email for confirmation
     * @param numberOfGuests Number of guests for the reservation
     */
    public Reservation(String guestName, String roomType, LocalDate checkInDate, 
                       LocalDate checkOutDate, String contactEmail, int numberOfGuests) {
        this.reservationId = ++reservationCounter;
        this.guestName = guestName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.requestTime = LocalDateTime.now();
        this.contactEmail = contactEmail;
        this.numberOfGuests = numberOfGuests;
    }
    
    /**
     * Get the reservation ID
     * 
     * @return Unique identifier for this reservation
     */
    public int getReservationId() {
        return reservationId;
    }
    
    /**
     * Get the guest name
     * 
     * @return Name of the guest
     */
    public String getGuestName() {
        return guestName;
    }
    
    /**
     * Get the room type requested
     * 
     * @return Type of room being booked
     */
    public String getRoomType() {
        return roomType;
    }
    
    /**
     * Get the check-in date
     * 
     * @return Check-in date for the reservation
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    /**
     * Get the check-out date
     * 
     * @return Check-out date for the reservation
     */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    /**
     * Get the request timestamp
     * 
     * @return Date and time when the reservation was created
     */
    public LocalDateTime getRequestTime() {
        return requestTime;
    }
    
    /**
     * Get the contact email
     * 
     * @return Guest's email address
     */
    public String getContactEmail() {
        return contactEmail;
    }
    
    /**
     * Get the number of guests
     * 
     * @return Number of guests for this reservation
     */
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    
    /**
     * Get number of nights for the reservation
     * 
     * @return Number of nights between check-in and check-out
     */
    public long getNumberOfNights() {
        return java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    
    /**
     * Display reservation details in a readable format
     */
    public void displayReservationDetails() {
        System.out.println("Reservation ID: #" + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Check-in: " + checkInDate);
        System.out.println("Check-out: " + checkOutDate);
        System.out.println("Number of Nights: " + getNumberOfNights());
        System.out.println("Number of Guests: " + numberOfGuests);
        System.out.println("Contact Email: " + contactEmail);
        System.out.println("Request Time: " + requestTime);
    }
    
    @Override
    public String toString() {
        return "Reservation{" +
                "ID=" + reservationId +
                ", Guest='" + guestName + '\'' +
                ", Room='" + roomType + '\'' +
                ", CheckIn=" + checkInDate +
                ", CheckOut=" + checkOutDate +
                ", Time=" + requestTime +
                '}';
    }
}
