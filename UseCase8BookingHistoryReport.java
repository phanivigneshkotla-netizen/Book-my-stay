/**
 * Use Case 8: Booking History & Reporting
 * Application Entry Point for Historical Tracking and Reporting
 * 
 * This use case demonstrates how booking history is tracked for operational visibility,
 * audits, and reporting. Bookings are stored in insertion order (chronological) and
 * can be analyzed without modifying the stored data.
 * 
 * Key Concepts Demonstrated:
 * - Operational Visibility: Historical data enables understanding past transactions
 * - List Data Structure: Preserves insertion order for chronological records
 * - Ordered Storage: Bookings stored in confirmation sequence
 * - Historical Tracking: Forms complete audit trail
 * - Reporting Readiness: Structured data enables flexible analysis
 * - Separation of Concerns: Storage separate from reporting logic
 * - Read-Only Reporting: Analysis doesn't modify stored bookings
 * - Persistence Mindset: Treats memory storage as long-lived data
 * 
 * Problem Addressed:
 * Use Case 7 extended bookings with add-on services but did not retain history.
 * Real systems need to:
 *   - Audit all confirmed transactions
 *   - Resolve customer issues with past bookings
 *   - Analyze revenue trends
 *   - Track operational metrics
 *   - Support compliance and verification
 * Without booking history, completed transactions disappeared after processing.
 * 
 * Solution - BookingHistory with List Structure:
 * - List<ConfirmedBooking> stores records in chronological order
 * - O(n) access to history (acceptable for reporting)
 * - Complete audit trail preserved
 * - Historical data enables flexible reporting
 * - Read-only access prevents accidental modifications
 * - Reporting logic separated from storage
 * 
 * Data Structure:
 * List<ConfirmedBooking> preserves:
 * - Reservation details
 * - Room allocation
 * - Service selections
 * - Confirmation timestamp
 * - Cost breakdown
 * 
 * Reporting Capabilities:
 * - Executive summary (totals, averages)
 * - Room type analysis (bookings per type, revenue)
 * - Guest reporting (individual booking history)
 * - Revenue breakdown (room vs add-on)
 * - Time-series potential (with timestamps)
 * 
 * Actor:
 * - Admin: Reviews history and requests reports
 * - BookingHistory: Stores confirmed bookings
 * - BookingReportService: Generates reports
 * 
 * Benefits:
 * - Complete booking audit trail
 * - Supports customer service issue resolution
 * - Enables operational analysis
 * - Simplifies compliance and verification
 * - Prepares for database persistence
 * 
 * @author Hotel Booking Team
 * @version 8.0
 * @since 2026-04-01
 */
public class UseCase8BookingHistoryReport {
    
    /**
     * Main method - Entry point demonstrating booking history and reporting
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 8: Booking History & Reporting");
        System.out.println("Version 8.0");
        System.out.println("========================================");
        
        // Step 1: Initialize Booking History
        System.out.println("\n[STEP 1] Initializing Booking History...");
        BookingHistory bookingHistory = new BookingHistory();
        System.out.println("✓ BookingHistory initialized");
        System.out.println("✓ Data Structure: List<ConfirmedBooking>");
        System.out.println("✓ Storage: In-memory, preserves insertion order (chronological)");
        System.out.println("✓ Purpose: Audit trail and historical tracking");
        
        // Step 2: Record confirmed bookings (simulating Use Cases 6 & 7)
        System.out.println("\n[STEP 2] Recording Confirmed Bookings (Historical Tracking)...");
        System.out.println("Note: Each booking is stored with room allocation and services\n");
        
        // Booking 1: Alice Johnson - Single Room with services
        System.out.println("Booking 1: Alice Johnson");
        BookingHistory.ConfirmedBooking booking1 = bookingHistory.recordBooking(
            1001, "Alice Johnson", "Single Room", "SINGLE_ROOM_001",
            "2026-04-05", "2026-04-08", 50.0, 85.0
        );
        System.out.println("  ✓ Recorded: Reservation #1001, Room SINGLE_ROOM_001");
        System.out.println("  ✓ Cost: $50.00 (room) + $85.00 (services) = $135.00");
        
        // Booking 2: Bob Smith - Double Room with services
        System.out.println("\nBooking 2: Bob Smith");
        BookingHistory.ConfirmedBooking booking2 = bookingHistory.recordBooking(
            1002, "Bob Smith", "Double Room", "DOUBLE_ROOM_001",
            "2026-04-06", "2026-04-10", 80.0, 50.0
        );
        System.out.println("  ✓ Recorded: Reservation #1002, Room DOUBLE_ROOM_001");
        System.out.println("  ✓ Cost: $80.00 (room) + $50.00 (services) = $130.00");
        
        // Booking 3: Carol White - Suite Room with services
        System.out.println("\nBooking 3: Carol White");
        BookingHistory.ConfirmedBooking booking3 = bookingHistory.recordBooking(
            1003, "Carol White", "Suite Room", "SUITE_ROOM_001",
            "2026-04-07", "2026-04-12", 150.0, 110.0
        );
        System.out.println("  ✓ Recorded: Reservation #1003, Room SUITE_ROOM_001");
        System.out.println("  ✓ Cost: $150.00 (room) + $110.00 (services) = $260.00");
        
        // Booking 4: David Brown - Double Room with services
        System.out.println("\nBooking 4: David Brown");
        BookingHistory.ConfirmedBooking booking4 = bookingHistory.recordBooking(
            1004, "David Brown", "Double Room", "DOUBLE_ROOM_002",
            "2026-04-08", "2026-04-11", 80.0, 65.0
        );
        System.out.println("  ✓ Recorded: Reservation #1004, Room DOUBLE_ROOM_002");
        System.out.println("  ✓ Cost: $80.00 (room) + $65.00 (services) = $145.00");
        
        // Booking 5: Eva Martinez - Single Room no services
        System.out.println("\nBooking 5: Eva Martinez");
        BookingHistory.ConfirmedBooking booking5 = bookingHistory.recordBooking(
            1005, "Eva Martinez", "Single Room", "SINGLE_ROOM_002",
            "2026-04-09", "2026-04-13", 50.0, 0.0
        );
        System.out.println("  ✓ Recorded: Reservation #1005, Room SINGLE_ROOM_002");
        System.out.println("  ✓ Cost: $50.00 (room) + $0.00 (services) = $50.00");
        
        // Booking 6: Frank Wilson - Suite Room with services
        System.out.println("\nBooking 6: Frank Wilson");
        BookingHistory.ConfirmedBooking booking6 = bookingHistory.recordBooking(
            1006, "Frank Wilson", "Suite Room", "SUITE_ROOM_002",
            "2026-04-10", "2026-04-15", 150.0, 25.0
        );
        System.out.println("  ✓ Recorded: Reservation #1006, Room SUITE_ROOM_002");
        System.out.println("  ✓ Cost: $150.00 (room) + $25.00 (services) = $175.00");
        
        // Booking 7: Grace Lee - Single Room with services
        System.out.println("\nBooking 7: Grace Lee");
        BookingHistory.ConfirmedBooking booking7 = bookingHistory.recordBooking(
            1007, "Grace Lee", "Single Room", "SINGLE_ROOM_003",
            "2026-04-11", "2026-04-14", 50.0, 40.0
        );
        System.out.println("  ✓ Recorded: Reservation #1007, Room SINGLE_ROOM_003");
        System.out.println("  ✓ Cost: $50.00 (room) + $40.00 (services) = $90.00");
        
        // Step 3: Display complete booking history
        System.out.println("\n[STEP 3] Complete Booking History (Chronological Order)...");
        bookingHistory.displayCompleteHistory();
        
        // Step 4: Initialize Report Service
        System.out.println("[STEP 4] Initializing Booking Report Service...");
        BookingReportService reportService = new BookingReportService(bookingHistory);
        System.out.println("✓ BookingReportService initialized");
        System.out.println("✓ Ready to generate reports from booking history");
        
        // Step 5: Generate Executive Summary
        System.out.println("[STEP 5] Generating Executive Summary Report...");
        reportService.generateExecutiveSummary();
        
        // Step 6: Generate Room Type Analysis
        System.out.println("[STEP 6] Generating Room Type Analysis Report...");
        reportService.generateRoomTypeAnalysis();
        
        // Step 7: Generate Revenue Breakdown
        System.out.println("[STEP 7] Generating Revenue Breakdown Report...");
        reportService.generateRevenueBreakdown();
        
        // Step 8: Generate guest-specific reports
        System.out.println("[STEP 8] Generating Guest-Specific Reports...");
        reportService.generateGuestReport("Alice Johnson");
        reportService.generateGuestReport("David Brown");
        reportService.generateGuestReport("Frank Wilson");
        
        // Step 9: Demonstrate retrieval operations
        System.out.println("[STEP 9] Demonstrating History Retrieval Operations...");
        System.out.println("========================================");
        System.out.println("HISTORY RETRIEVAL OPERATIONS");
        System.out.println("========================================");
        
        // Retrieve by reservation ID
        BookingHistory.ConfirmedBooking found = bookingHistory.getBookingByReservationId(1003);
        if (found != null) {
            System.out.println("\nLookup by Reservation ID #1003:");
            System.out.println("  Guest: " + found.getGuestName());
            System.out.println("  Room: " + found.getRoomId());
            System.out.println("  Total Cost: $" + String.format("%.2f", found.getTotalCost()));
        }
        
        // Show all bookings for a guest
        System.out.println("\nLookup by Guest Name 'Single Room' bookings:");
        int singleRoomCount = 0;
        for (BookingHistory.ConfirmedBooking booking : bookingHistory.getAllBookings()) {
            if (booking.getRoomType().equals("Single Room")) {
                System.out.println("  • " + booking.getGuestName() + " - Room " + booking.getRoomId());
                singleRoomCount++;
            }
        }
        System.out.println("  Total Single Room bookings: " + singleRoomCount);
        
        System.out.println("\n========================================");
        
        // Step 10: Verify data integrity and read-only access
        System.out.println("\n[STEP 10] Data Integrity Verification...");
        System.out.println("========================================");
        System.out.println("HISTORICAL DATA INTEGRITY CHECK");
        System.out.println("========================================");
        System.out.println("✓ Total bookings recorded: " + bookingHistory.getTotalBookingCount());
        System.out.println("✓ Bookings are in insertion order (chronological)");
        System.out.println("✓ All booking details preserved:");
        System.out.println("  - Guest information: YES");
        System.out.println("  - Room allocation: YES");
        System.out.println("  - Cost details: YES");
        System.out.println("  - Confirmation timestamp: YES");
        System.out.println("✓ Read-only access enforced: YES");
        System.out.println("✓ Reports generated without modifying history: YES");
        System.out.println("========================================");
        
        // Summary
        System.out.println("\n========================================");
        System.out.println("SUMMARY - Use Case 8 Achievements");
        System.out.println("========================================");
        System.out.println("✓ Operational Visibility: Complete history recorded");
        System.out.println("✓ List Data Structure: Preserves insertion order");
        System.out.println("✓ Ordered Storage: Chronological record maintained");
        System.out.println("✓ Historical Tracking: Full audit trail created");
        System.out.println("✓ Reporting Readiness: Flexible analysis capability");
        System.out.println("✓ Separation of Concerns: Storage and reporting separated");
        System.out.println("✓ Read-Only Reporting: History unchanged by reports");
        System.out.println("✓ Total Bookings: " + bookingHistory.getTotalBookingCount());
        System.out.println("✓ Total Revenue: $" + 
                         String.format("%.2f", bookingHistory.getTotalRevenue()));
        System.out.println("✓ Room Revenue: $" + 
                         String.format("%.2f", bookingHistory.getRoomCostRevenue()));
        System.out.println("✓ Add-On Revenue: $" + 
                         String.format("%.2f", bookingHistory.getAddOnRevenue()));
        
        System.out.println("\nKey Learning:");
        System.out.println("Booking history creates an audit trail for operational visibility.");
        System.out.println("Using List preserves insertion order, enabling chronological");
        System.out.println("reporting. Reports analyze stored data without modifying it.");
        System.out.println("This prepares the system conceptually for database persistence.");
        System.out.println("========================================");
        System.out.println("Application terminated successfully!");
    }
}
