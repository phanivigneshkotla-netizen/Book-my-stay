import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * BookingHistory Class - Maintains historical records of confirmed bookings
 * 
 * This class stores confirmed reservations in a chronologically ordered list,
 * providing an audit trail and enabling reporting on past transactions.
 * This is the persistence layer for booking data without external storage.
 * 
 * Key Concepts:
 * - Operational Visibility: Real systems need historical data access
 * - List Data Structure: Preserves insertion order (chronological)
 * - Ordered Storage: Bookings stored in confirmation sequence
 * - Historical Tracking: Forms audit trail for verification
 * - Reporting Readiness: Structured data enables analysis
 * - Persistence Mindset: Treats memory storage as long-lived data
 * - Read-Only Access: History operations don't modify stored data
 * 
 * Data Structure:
 * List<ConfirmedBooking> stores booking records with:
 * - Reservation details (guest, room type, dates)
 * - Allocation details (room ID assigned)
 * - Service details (add-on services selected)
 * - Confirmation timestamp
 * - Confirmation status
 * 
 * Use Cases:
 * - Audit trail for compliance
 * - Customer service issue resolution
 * - Revenue analysis and reporting
 * - Operational statistics
 * - Historical trend analysis
 * 
 * @author Hotel Booking Team
 * @version 8.0
 * @since 2026-04-01
 */
public class BookingHistory {
    
    // List storing confirmed bookings in insertion order (chronological)
    // This naturally preserves the sequence of bookings
    private List<ConfirmedBooking> bookingHistory;
    
    /**
     * Constructor - Initializes empty booking history
     */
    public BookingHistory() {
        this.bookingHistory = new ArrayList<>();
    }
    
    /**
     * Record a confirmed booking in the history
     * This method is called when a reservation is successfully confirmed.
     * 
     * @param reservationId Reservation ID
     * @param guestName Guest name
     * @param roomType Room type booked
     * @param roomId Allocated room ID
     * @param checkInDate Check-in date string
     * @param checkOutDate Check-out date string
     * @param roomCost Cost of the room
     * @param addOnCost Cost of add-on services
     * @return The recorded confirmed booking
     */
    public ConfirmedBooking recordBooking(int reservationId, String guestName, 
                                         String roomType, String roomId,
                                         String checkInDate, String checkOutDate,
                                         double roomCost, double addOnCost) {
        ConfirmedBooking confirmedBooking = new ConfirmedBooking(
            reservationId, guestName, roomType, roomId,
            checkInDate, checkOutDate, roomCost, addOnCost
        );
        
        bookingHistory.add(confirmedBooking);
        return confirmedBooking;
    }
    
    /**
     * Get all confirmed bookings from history
     * This provides read-only access to the complete history.
     * 
     * @return List of all confirmed bookings in chronological order
     */
    public List<ConfirmedBooking> getAllBookings() {
        // Return copy to prevent external modification
        return new ArrayList<>(bookingHistory);
    }
    
    /**
     * Get booking by reservation ID
     * 
     * @param reservationId The reservation ID to search for
     * @return ConfirmedBooking if found, null otherwise
     */
    public ConfirmedBooking getBookingByReservationId(int reservationId) {
        for (ConfirmedBooking booking : bookingHistory) {
            if (booking.getReservationId() == reservationId) {
                return booking;
            }
        }
        return null;
    }
    
    /**
     * Get bookings for a specific guest
     * 
     * @param guestName The guest name to search for
     * @return List of bookings for this guest
     */
    public List<ConfirmedBooking> getBookingsByGuestName(String guestName) {
        List<ConfirmedBooking> guestBookings = new ArrayList<>();
        
        for (ConfirmedBooking booking : bookingHistory) {
            if (booking.getGuestName().equalsIgnoreCase(guestName)) {
                guestBookings.add(booking);
            }
        }
        
        return guestBookings;
    }
    
    /**
     * Get total number of confirmed bookings
     * 
     * @return Count of bookings in history
     */
    public int getTotalBookingCount() {
        return bookingHistory.size();
    }
    
    /**
     * Get total revenue from all bookings
     * 
     * @return Sum of room costs and add-on costs
     */
    public double getTotalRevenue() {
        double totalRevenue = 0.0;
        
        for (ConfirmedBooking booking : bookingHistory) {
            totalRevenue += booking.getTotalCost();
        }
        
        return totalRevenue;
    }
    
    /**
     * Get room cost revenue (excluding add-ons)
     * 
     * @return Sum of room costs only
     */
    public double getRoomCostRevenue() {
        double roomRevenue = 0.0;
        
        for (ConfirmedBooking booking : bookingHistory) {
            roomRevenue += booking.getRoomCost();
        }
        
        return roomRevenue;
    }
    
    /**
     * Get add-on service revenue
     * 
     * @return Sum of add-on costs
     */
    public double getAddOnRevenue() {
        double addOnRevenue = 0.0;
        
        for (ConfirmedBooking booking : bookingHistory) {
            addOnRevenue += booking.getAddOnCost();
        }
        
        return addOnRevenue;
    }
    
    /**
     * Display complete booking history in chronological order
     */
    public void displayCompleteHistory() {
        System.out.println("\n========================================");
        System.out.println("COMPLETE BOOKING HISTORY");
        System.out.println("(In Chronological Order)");
        System.out.println("========================================");
        
        if (bookingHistory.isEmpty()) {
            System.out.println("No bookings recorded yet.");
        } else {
            int position = 1;
            for (ConfirmedBooking booking : bookingHistory) {
                System.out.println("\n[" + position + "] Reservation #" + booking.getReservationId());
                System.out.println("-".repeat(40));
                System.out.println("Guest: " + booking.getGuestName());
                System.out.println("Room Type: " + booking.getRoomType());
                System.out.println("Room ID: " + booking.getRoomId());
                System.out.println("Check-in: " + booking.getCheckInDate());
                System.out.println("Check-out: " + booking.getCheckOutDate());
                System.out.println("Room Cost: $" + String.format("%.2f", booking.getRoomCost()));
                System.out.println("Add-On Cost: $" + String.format("%.2f", booking.getAddOnCost()));
                System.out.println("Total: $" + String.format("%.2f", booking.getTotalCost()));
                System.out.println("Confirmed: " + booking.getConfirmationTimestamp());
                position++;
            }
        }
        
        System.out.println("\n========================================\n");
    }
    
    /**
     * Inner class representing a confirmed booking record
     */
    public static class ConfirmedBooking {
        private int reservationId;
        private String guestName;
        private String roomType;
        private String roomId;
        private String checkInDate;
        private String checkOutDate;
        private double roomCost;
        private double addOnCost;
        private LocalDateTime confirmationTimestamp;
        
        public ConfirmedBooking(int reservationId, String guestName, String roomType,
                               String roomId, String checkInDate, String checkOutDate,
                               double roomCost, double addOnCost) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.roomCost = roomCost;
            this.addOnCost = addOnCost;
            this.confirmationTimestamp = LocalDateTime.now();
        }
        
        // Getters
        public int getReservationId() { return reservationId; }
        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
        public String getRoomId() { return roomId; }
        public String getCheckInDate() { return checkInDate; }
        public String getCheckOutDate() { return checkOutDate; }
        public double getRoomCost() { return roomCost; }
        public double getAddOnCost() { return addOnCost; }
        public double getTotalCost() { return roomCost + addOnCost; }
        public LocalDateTime getConfirmationTimestamp() { return confirmationTimestamp; }
    }
}
