import java.time.LocalDate;

/**
 * InvalidDateRangeException - Exception thrown for invalid check-in/check-out dates
 * 
 * This exception indicates that the date range for a reservation is invalid,
 * such as check-out before check-in or dates in the past.
 * 
 * @author Hotel Booking Team
 * @version 9.0
 * @since 2026-04-01
 */
public class InvalidDateRangeException extends InvalidBookingException {
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
    /**
     * Constructor - Creates exception with date information
     * 
     * @param checkInDate The check-in date
     * @param checkOutDate The check-out date
     * @param reason The reason why the dates are invalid
     */
    public InvalidDateRangeException(LocalDate checkInDate, LocalDate checkOutDate, String reason) {
        super("Invalid date range: Check-in " + checkInDate + ", Check-out " + checkOutDate + 
              ". Reason: " + reason);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
    
    /**
     * Get the check-in date
     * 
     * @return The check-in date
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    /**
     * Get the check-out date
     * 
     * @return The check-out date
     */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}
