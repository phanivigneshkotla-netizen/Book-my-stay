import java.time.LocalDate;
import java.util.Scanner;

/**
 * UseCase9ErrorHandlingValidation - Comprehensive Error Handling & Validation
 * 
 * BUSINESS REQUIREMENT:
 * The system must validate all booking requests and system state before processing.
 * Invalid inputs or inconsistent states should be detected early and handled gracefully
 * without corrupting the system. The system must maintain correctness through:
 * - Input validation of all guest information
 * - Constraint checking on dates and inventory
 * - Custom exceptions for domain-specific errors
 * - Fail-fast design to prevent invalid state changes
 * - Graceful error recovery
 * 
 * TECHNICAL SOLUTION:
 * - Custom exception hierarchy (5 exception classes)
 * - BookingValidator class with comprehensive validation methods
 * - Try-catch blocks for graceful error handling
 * - Error information provided to users for correction
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * 1. Input Validation: All guest details validated before processing
 * 2. Constraint Checking: Business rules enforced (date ranges, inventory)
 * 3. Custom Exceptions: Domain-specific errors communicate precise problems
 * 4. Fail-Fast Design: Errors detected immediately and reported
 * 5. Graceful Failure: System remains stable after errors occur
 * 6. Error Recovery: Users can correct invalid input and try again
 * 
 * VALIDATION SCENARIOS:
 * - Valid booking (all checks pass) ✓
 * - Invalid guest name (null, empty, too short) ✗
 * - Invalid email (missing @, malformed) ✗
 * - Invalid date range (past dates, checkout before checkin) ✗
 * - Room type not available ✗
 * - Number of guests invalid (zero, too many) ✗
 * 
 * @author Hotel Booking Team
 * @version 9.0
 * @since 2026-04-01
 */
public class UseCase9ErrorHandlingValidation {
    
    // System state
    private static RoomInventory inventory;
    private static BookingValidator validator;
    private static int scenarioCount = 0;
    
    /**
     * Main entry point - Demonstrates error handling and validation
     */
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Use Case 9: Error Handling & Validation in Hotel Booking     ║");
        System.out.println("║  Comprehensive Input Validation & Exception Handling          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Initialize system
        initializeSystem();
        
        // Run validation scenarios
        System.out.println("\n════ VALIDATION SCENARIO DEMONSTRATIONS ════\n");
        
        // Scenario 1: Valid booking (all validations pass)
        demonstrateValidBooking();
        
        // Scenario 2: Invalid guest name
        demonstrateInvalidGuestName();
        
        // Scenario 3: Invalid email
        demonstrateInvalidEmail();
        
        // Scenario 4: Invalid date range (past date)
        demonstrateInvalidPastDate();
        
        // Scenario 5: Invalid date range (checkout before checkin)
        demonstrateInvalidDateOrder();
        
        // Scenario 6: Invalid number of guests
        demonstrateInvalidNumberOfGuests();
        
        // Scenario 7: Room not available
        demonstrateRoomNotAvailable();
        
        System.out.println("\n════ VALIDATION SUMMARY ════");
        System.out.println("Total Scenarios Demonstrated: " + scenarioCount);
        System.out.println("✓ Valid bookings protected by comprehensive validation");
        System.out.println("✗ Invalid bookings rejected with descriptive error messages");
        System.out.println("→ System remains stable and consistent after all error scenarios");
        System.out.println("\nUse Case 9 - Error Handling & Validation Successfully Demonstrated!");
    }
    
    /**
     * Initialize system with inventory and validator
     */
    private static void initializeSystem() {
        inventory = new RoomInventory();
        
        // Initialize inventory with room types
        inventory.addRoom("Single", 10);
        inventory.addRoom("Double", 8);
        inventory.addRoom("Suite", 5);
        
        System.out.println("✓ Room Inventory Initialized:");
        System.out.println("  - Single rooms: " + inventory.getAvailability("Single") + " available");
        System.out.println("  - Double rooms: " + inventory.getAvailability("Double") + " available");
        System.out.println("  - Suite rooms: " + inventory.getAvailability("Suite") + " available");
        
        // Initialize validator
        validator = new BookingValidator(inventory);
        System.out.println("✓ Booking Validator Initialized\n");
    }
    
    /**
     * Scenario 1: Valid Booking - All validations pass successfully
     */
    private static void demonstrateValidBooking() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": VALID BOOKING");
        System.out.println("─────────────────────────────────────────────────");
        
        try {
            String guestName = "John Smith";
            String email = "john.smith@email.com";
            String roomType = "Double";
            LocalDate checkIn = LocalDate.now().plusDays(5);
            LocalDate checkOut = LocalDate.now().plusDays(7);
            int guests = 2;
            
            System.out.println("Attempting to book:");
            System.out.println("  Guest: " + guestName);
            System.out.println("  Email: " + email);
            System.out.println("  Room: " + roomType);
            System.out.println("  Check-in: " + checkIn);
            System.out.println("  Check-out: " + checkOut);
            System.out.println("  Guests: " + guests);
            System.out.println("  Status: Running validations...");
            
            // Run comprehensive validation
            validator.validateCompleteBooking(guestName, email, roomType, checkIn, checkOut, guests);
            
            System.out.println("✓ ALL VALIDATIONS PASSED");
            System.out.println("✓ Booking is valid and can be processed");
            
        } catch (InvalidBookingException e) {
            System.out.println("✗ Booking rejected: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 2: Invalid Guest Name - Name validation fails
     */
    private static void demonstrateInvalidGuestName() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": INVALID GUEST NAME");
        System.out.println("─────────────────────────────────────────────────");
        
        // Test Case 2a: Null name
        System.out.println("Test Case 2a: Null Guest Name");
        try {
            validator.validateGuestName(null);
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
        
        // Test Case 2b: Empty name
        System.out.println("Test Case 2b: Empty Guest Name");
        try {
            validator.validateGuestName("");
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
        
        // Test Case 2c: Single character name
        System.out.println("Test Case 2c: Single Character Guest Name");
        try {
            validator.validateGuestName("A");
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 3: Invalid Email - Email validation fails
     */
    private static void demonstrateInvalidEmail() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": INVALID EMAIL");
        System.out.println("─────────────────────────────────────────────────");
        
        // Test Case 3a: Email without @
        System.out.println("Test Case 3a: Email Without @ Symbol");
        try {
            validator.validateEmail("johnsmith.email.com");
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
        
        // Test Case 3b: Empty email
        System.out.println("Test Case 3b: Empty Email");
        try {
            validator.validateEmail("");
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
        
        // Test Case 3c: Null email
        System.out.println("Test Case 3c: Null Email");
        try {
            validator.validateEmail(null);
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 4: Invalid Date Range - Check-in date is in the past
     */
    private static void demonstrateInvalidPastDate() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": INVALID DATE - PAST DATE");
        System.out.println("─────────────────────────────────────────────────");
        
        try {
            LocalDate pastDate = LocalDate.now().minusDays(10);
            LocalDate futureDate = LocalDate.now().plusDays(5);
            
            System.out.println("Attempting to book with check-in date in the past:");
            System.out.println("  Check-in: " + pastDate + " (Past date - Invalid!)");
            System.out.println("  Check-out: " + futureDate);
            
            validator.validateDateRange(pastDate, futureDate);
            System.out.println("✗ Validation should have failed");
            
        } catch (InvalidDateRangeException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 5: Invalid Date Range - Check-out before Check-in
     */
    private static void demonstrateInvalidDateOrder() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": INVALID DATE - WRONG ORDER");
        System.out.println("─────────────────────────────────────────────────");
        
        try {
            LocalDate checkOut = LocalDate.now().plusDays(2);
            LocalDate checkIn = LocalDate.now().plusDays(5);
            
            System.out.println("Attempting to book with check-out before check-in:");
            System.out.println("  Check-in: " + checkIn);
            System.out.println("  Check-out: " + checkOut + " (Before check-in - Invalid!)");
            
            validator.validateDateRange(checkIn, checkOut);
            System.out.println("✗ Validation should have failed");
            
        } catch (InvalidDateRangeException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 6: Invalid Number of Guests
     */
    private static void demonstrateInvalidNumberOfGuests() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": INVALID NUMBER OF GUESTS");
        System.out.println("─────────────────────────────────────────────────");
        
        // Test Case 6a: Zero guests
        System.out.println("Test Case 6a: Zero Guests");
        try {
            validator.validateNumberOfGuests(0);
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
        
        // Test Case 6b: Negative guests
        System.out.println("Test Case 6b: Negative Guests");
        try {
            validator.validateNumberOfGuests(-5);
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
        
        // Test Case 6c: Too many guests
        System.out.println("Test Case 6c: Exceeds Maximum Guests Per Room");
        try {
            validator.validateNumberOfGuests(15);
            System.out.println("✗ Validation should have failed");
        } catch (InvalidGuestInformationException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Scenario 7: Room Not Available
     */
    private static void demonstrateRoomNotAvailable() {
        scenarioCount++;
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("Scenario " + scenarioCount + ": ROOM NOT AVAILABLE");
        System.out.println("─────────────────────────────────────────────────");
        
        // Book all available suites
        System.out.println("Test Case 7a: All Rooms Fully Booked");
        System.out.println("Current Suite availability: " + inventory.getAvailability("Suite"));
        
        // Simulate booking all suites
        for (int i = 0; i < 5; i++) {
            inventory.bookRoom("Suite");
        }
        System.out.println("After booking all 5 suites: " + inventory.getAvailability("Suite") + " available");
        
        // Try to book when all suites are taken
        try {
            validator.validateAvailability("Suite");
            System.out.println("✗ Validation should have failed");
        } catch (RoomNotAvailableException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
        
        // Restore inventory
        for (int i = 0; i < 5; i++) {
            inventory.cancelBooking("Suite");
        }
        System.out.println("Suites restored: " + inventory.getAvailability("Suite") + " available");
        System.out.println();
    }
}
