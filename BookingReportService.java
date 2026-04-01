import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BookingReportService Class - Generates reports from booking history
 * 
 * This class provides various reporting capabilities based on stored booking data.
 * It performs analysis and summarization without modifying the underlying history.
 * Reports are generated on-demand from the booking history data.
 * 
 * Key Concepts:
 * - Reporting Readiness: Structured booking data enables flexible reporting
 * - Separation of Concerns: Reporting logic separate from data storage
 * - Read-Only Operations: Analysis doesn't modify stored bookings
 * - Data Aggregation: Summarizes history for insights
 * - Business Intelligence: Provides operational visibility
 * 
 * Report Types:
 * - Booking Summary: Total bookings and revenue
 * - Revenue Analysis: Room vs add-on breakdown
 * - Room Type Analysis: Bookings and revenue by room type
 * - Guest Reporting: Individual guest booking history
 * - Time-based Analysis: Trend identification
 * 
 * @author Hotel Booking Team
 * @version 8.0
 * @since 2026-04-01
 */
public class BookingReportService {
    
    // Reference to booking history
    private BookingHistory bookingHistory;
    
    /**
     * Constructor - Initializes report service with booking history
     * 
     * @param bookingHistory The booking history to analyze
     */
    public BookingReportService(BookingHistory bookingHistory) {
        if (bookingHistory == null) {
            throw new IllegalArgumentException("Booking history cannot be null");
        }
        this.bookingHistory = bookingHistory;
    }
    
    /**
     * Generate executive summary report
     */
    public void generateExecutiveSummary() {
        System.out.println("\n========================================");
        System.out.println("EXECUTIVE SUMMARY REPORT");
        System.out.println("========================================");
        
        int totalBookings = bookingHistory.getTotalBookingCount();
        double totalRevenue = bookingHistory.getTotalRevenue();
        double roomRevenue = bookingHistory.getRoomCostRevenue();
        double addOnRevenue = bookingHistory.getAddOnRevenue();
        
        System.out.println("\nBooking Statistics:");
        System.out.println("  Total Bookings: " + totalBookings);
        System.out.println("  Average Booking Value: $" + 
                         String.format("%.2f", totalBookings > 0 ? totalRevenue / totalBookings : 0));
        
        System.out.println("\nRevenue Summary:");
        System.out.println("  Total Revenue: $" + String.format("%.2f", totalRevenue));
        System.out.println("  Room Revenue: $" + String.format("%.2f", roomRevenue) + 
                         " (" + String.format("%.1f", totalRevenue > 0 ? (roomRevenue / totalRevenue * 100) : 0) + "%)");
        System.out.println("  Add-On Revenue: $" + String.format("%.2f", addOnRevenue) + 
                         " (" + String.format("%.1f", totalRevenue > 0 ? (addOnRevenue / totalRevenue * 100) : 0) + "%)");
        
        System.out.println("\n========================================\n");
    }
    
    /**
     * Generate room type analysis report
     */
    public void generateRoomTypeAnalysis() {
        System.out.println("\n========================================");
        System.out.println("ROOM TYPE ANALYSIS REPORT");
        System.out.println("========================================");
        
        // Aggregate bookings by room type
        Map<String, RoomTypeStats> roomStats = new HashMap<>();
        
        for (BookingHistory.ConfirmedBooking booking : bookingHistory.getAllBookings()) {
            String roomType = booking.getRoomType();
            roomStats.putIfAbsent(roomType, new RoomTypeStats(roomType));
            roomStats.get(roomType).addBooking(booking);
        }
        
        if (roomStats.isEmpty()) {
            System.out.println("No bookings to analyze.");
        } else {
            System.out.println("\nRoom Type Breakdown:");
            System.out.println("-".repeat(40));
            
            for (RoomTypeStats stats : roomStats.values()) {
                System.out.println("\n" + stats.roomType);
                System.out.println("  Bookings: " + stats.getBookingCount());
                System.out.println("  Room Revenue: $" + String.format("%.2f", stats.getRoomRevenue()));
                System.out.println("  Add-On Revenue: $" + String.format("%.2f", stats.getAddOnRevenue()));
                System.out.println("  Total Revenue: $" + String.format("%.2f", stats.getTotalRevenue()));
                System.out.println("  Average per Booking: $" + 
                                 String.format("%.2f", stats.getAveragePerBooking()));
            }
        }
        
        System.out.println("\n========================================\n");
    }
    
    /**
     * Generate guest report for a specific guest
     * 
     * @param guestName The guest name to report on
     */
    public void generateGuestReport(String guestName) {
        System.out.println("\n========================================");
        System.out.println("GUEST BOOKING REPORT: " + guestName);
        System.out.println("========================================");
        
        List<BookingHistory.ConfirmedBooking> guestBookings = 
            bookingHistory.getBookingsByGuestName(guestName);
        
        if (guestBookings.isEmpty()) {
            System.out.println("No bookings found for guest: " + guestName);
        } else {
            double totalSpent = 0.0;
            System.out.println("\nGuest Booking History:");
            System.out.println("-".repeat(40));
            
            int count = 1;
            for (BookingHistory.ConfirmedBooking booking : guestBookings) {
                System.out.println("\n[" + count + "] Reservation #" + booking.getReservationId());
                System.out.println("  Room Type: " + booking.getRoomType());
                System.out.println("  Room ID: " + booking.getRoomId());
                System.out.println("  Check-in: " + booking.getCheckInDate());
                System.out.println("  Check-out: " + booking.getCheckOutDate());
                System.out.println("  Total Cost: $" + String.format("%.2f", booking.getTotalCost()));
                totalSpent += booking.getTotalCost();
                count++;
            }
            
            System.out.println("\n" + "-".repeat(40));
            System.out.println("Total Bookings: " + guestBookings.size());
            System.out.println("Total Spent: $" + String.format("%.2f", totalSpent));
            System.out.println("Average per Booking: $" + 
                             String.format("%.2f", totalSpent / guestBookings.size()));
        }
        
        System.out.println("\n========================================\n");
    }
    
    /**
     * Generate revenue breakdown report
     */
    public void generateRevenueBreakdown() {
        System.out.println("\n========================================");
        System.out.println("REVENUE BREAKDOWN REPORT");
        System.out.println("========================================");
        
        double totalRevenue = bookingHistory.getTotalRevenue();
        double roomRevenue = bookingHistory.getRoomCostRevenue();
        double addOnRevenue = bookingHistory.getAddOnRevenue();
        
        System.out.println("\nTotal Revenue: $" + String.format("%.2f", totalRevenue));
        System.out.println("-".repeat(40));
        System.out.println("Room Revenue: $" + String.format("%.2f", roomRevenue));
        System.out.println("  (Percentage: " + 
                         String.format("%.1f", totalRevenue > 0 ? (roomRevenue / totalRevenue * 100) : 0) + "%)");
        System.out.println("\nAdd-On Services Revenue: $" + String.format("%.2f", addOnRevenue));
        System.out.println("  (Percentage: " + 
                         String.format("%.1f", totalRevenue > 0 ? (addOnRevenue / totalRevenue * 100) : 0) + "%)");
        
        System.out.println("\n========================================\n");
    }
    
    /**
     * Inner class for room type statistics
     */
    private static class RoomTypeStats {
        private String roomType;
        private int bookingCount;
        private double roomRevenue;
        private double addOnRevenue;
        
        public RoomTypeStats(String roomType) {
            this.roomType = roomType;
            this.bookingCount = 0;
            this.roomRevenue = 0.0;
            this.addOnRevenue = 0.0;
        }
        
        public void addBooking(BookingHistory.ConfirmedBooking booking) {
            bookingCount++;
            roomRevenue += booking.getRoomCost();
            addOnRevenue += booking.getAddOnCost();
        }
        
        public int getBookingCount() { return bookingCount; }
        public double getRoomRevenue() { return roomRevenue; }
        public double getAddOnRevenue() { return addOnRevenue; }
        public double getTotalRevenue() { return roomRevenue + addOnRevenue; }
        public double getAveragePerBooking() { 
            return bookingCount > 0 ? getTotalRevenue() / bookingCount : 0; 
        }
    }
}
