/**
 * InvalidBookingException - Base custom exception for booking-related errors
 * 
 * This exception represents errors in the booking process that prevent
 * valid reservation confirmation.
 * 
 * @author Hotel Booking Team
 * @version 9.0
 * @since 2026-04-01
 */
public class InvalidBookingException extends Exception {
    
    /**
     * Constructor - Creates exception with error message
     * 
     * @param message Detailed error message
     */
    public InvalidBookingException(String message) {
        super(message);
    }
    
    /**
     * Constructor - Creates exception with message and cause
     * 
     * @param message Detailed error message
     * @param cause The underlying cause of the exception
     */
    public InvalidBookingException(String message, Throwable cause) {
        super(message, cause);
    }
}
