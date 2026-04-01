/**
 * InvalidGuestInformationException - Exception thrown for invalid guest details
 * 
 * This exception indicates that the guest provided invalid personal information,
 * such as empty name or invalid email.
 * 
 * @author Hotel Booking Team
 * @version 9.0
 * @since 2026-04-01
 */
public class InvalidGuestInformationException extends InvalidBookingException {
    
    private String fieldName;
    private String invalidValue;
    
    /**
     * Constructor - Creates exception with field information
     * 
     * @param fieldName The name of the invalid field (e.g., "Guest Name")
     * @param invalidValue The invalid value provided
     * @param reason The reason why the value is invalid
     */
    public InvalidGuestInformationException(String fieldName, String invalidValue, String reason) {
        super("Invalid " + fieldName + ": '" + invalidValue + "'. Reason: " + reason);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    /**
     * Get the field name that was invalid
     * 
     * @return The field name
     */
    public String getFieldName() {
        return fieldName;
    }
    
    /**
     * Get the invalid value
     * 
     * @return The value that was invalid
     */
    public String getInvalidValue() {
        return invalidValue;
    }
}
