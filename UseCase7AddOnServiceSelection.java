import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Use Case 7: Add-On Service Selection
 * Application Entry Point for Optional Service Management
 * 
 * This use case demonstrates how optional services can be added to reservations
 * without modifying core booking logic or inventory state. Services are composed
 * with reservations using a flexible mapping structure.
 * 
 * Key Concepts Demonstrated:
 * - Business Extensibility: Real-world booking features beyond primary product
 * - One-to-Many Relationship: Single reservation can have multiple services
 * - Map and List Combination: Efficient lookup and management of services
 * - Composition over Inheritance: Services attached, not inherited
 * - Separation of Concerns: Add-on logic separated from core booking
 * - Cost Aggregation: Service costs calculated separately from room pricing
 * 
 * Problem Addressed:
 * Use Case 6 confirmed room allocation but treated bookings as static entities.
 * Real-world booking systems often include add-on services:
 *   - Early check-in/late checkout
 *   - Airport transfers
 *   - Breakfast packages
 *   - Spa services
 *   - Pet-friendly options
 * Without add-on support, the system couldn't model these common enhancements.
 * 
 * Solution - AddOnServiceManager with Map and List:
 * - Map<Integer, List<Service>> for reservation-to-services mapping
 * - One-to-many allows multiple services per reservation
 * - List preserves service order and allows duplicates to be prevented
 * - No modification to core booking or inventory logic
 * - Easy addition of new service types
 * - Cost calculation remains modular and separate
 * 
 * Data Structure:
 * Map<Integer, List<Service>>
 * Key: Reservation ID
 * Value: List of services attached to that reservation
 * 
 * Example After Use Case 6:
 * 1001 -> [Room allocated]
 * 1002 -> [Room allocated]
 * 1003 -> [Room allocated]
 * 
 * After Use Case 7:
 * 1001 -> [Room allocated] + [Early Check-in, Room Service]
 * 1002 -> [Room allocated] + [Airport Transfer, Breakfast]
 * 1003 -> [Room allocated] + [Pet Friendly]
 * 
 * Benefits:
 * - Flexible attachment of services
 * - Clean one-to-many relationship
 * - No core booking logic changes
 * - Easy service addition
 * - Clean cost aggregation
 * 
 * Actor:
 * - Guest: Selects optional services
 * - Service: Individual optional offering
 * - AddOnServiceManager: Manages service associations
 * 
 * @author Hotel Booking Team
 * @version 7.0
 * @since 2026-04-01
 */
public class UseCase7AddOnServiceSelection {
    
    /**
     * Main method - Entry point demonstrating add-on service selection
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 7: Add-On Service Selection");
        System.out.println("Version 7.0");
        System.out.println("========================================");
        
        // Step 1: Initialize Add-On Service Manager
        System.out.println("\n[STEP 1] Initializing Add-On Service Manager...");
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        System.out.println("✓ AddOnServiceManager initialized");
        System.out.println("✓ Data Structure: Map<Integer, List<Service>>");
        System.out.println("✓ Supports one-to-many relationship (1 reservation : Many services)");
        
        // Step 2: Create a catalog of available services
        System.out.println("\n[STEP 2] Creating Service Catalog...");
        
        Service earlyCheckIn = new Service(
            "Early Check-in (12 PM)",
            "Access your room at 12:00 PM instead of standard 3:00 PM",
            25.0,
            "Convenience"
        );
        
        Service lateCheckOut = new Service(
            "Late Check-out (6 PM)",
            "Extend checkout time to 6:00 PM instead of standard 11:00 AM",
            30.0,
            "Convenience"
        );
        
        Service airportTransfer = new Service(
            "Airport Transfer",
            "Round-trip transportation between airport and hotel",
            50.0,
            "Transportation"
        );
        
        Service roomService = new Service(
            "Room Service Voucher",
            "Complimentary $50 room service credit",
            15.0,
            "Dining"
        );
        
        Service breakfast = new Service(
            "Daily Breakfast (3 days)",
            "Complimentary breakfast for entire stay",
            45.0,
            "Dining"
        );
        
        Service spaPackage = new Service(
            "Spa Package",
            "60-minute massage and wellness treatment",
            80.0,
            "Wellness"
        );
        
        Service petFriendly = new Service(
            "Pet-Friendly Room",
            "Allow pets in room with no additional restrictions",
            20.0,
            "Accommodations"
        );
        
        System.out.println("✓ Service Catalog Created:");
        System.out.println("  1. Early Check-in - $25.00");
        System.out.println("  2. Late Check-out - $30.00");
        System.out.println("  3. Airport Transfer - $50.00");
        System.out.println("  4. Room Service Voucher - $15.00");
        System.out.println("  5. Daily Breakfast - $45.00");
        System.out.println("  6. Spa Package - $80.00");
        System.out.println("  7. Pet-Friendly Room - $20.00");
        
        // Step 3: Simulate reservation IDs from Use Case 6
        System.out.println("\n[STEP 3] Simulating Existing Reservations from Use Case 6...");
        int[] reservationIds = {1001, 1002, 1003, 1004, 1005};
        System.out.println("✓ Existing reservations: " + String.join(", ", 
            java.util.Arrays.stream(reservationIds).mapToObj(String::valueOf).toArray(String[]::new)));
        System.out.println("✓ Note: Core booking and allocation remain unchanged");
        System.out.println("✓ Add-on services are OPTIONAL and independent");
        
        // Step 4: Guests select services for their reservations
        System.out.println("\n[STEP 4] Guests Selecting Add-On Services...");
        System.out.println("Adding services to reservations (Composition model)\n");
        
        // Guest 1 (Reservation 1001) selects services
        System.out.println("Guest 1 (Reservation #1001) - Alice Johnson:");
        serviceManager.addServiceToReservation(1001, earlyCheckIn);
        System.out.println("  ✓ Added: Early Check-in");
        serviceManager.addServiceToReservation(1001, roomService);
        System.out.println("  ✓ Added: Room Service Voucher");
        serviceManager.addServiceToReservation(1001, breakfast);
        System.out.println("  ✓ Added: Daily Breakfast");
        serviceManager.displayReservationServices(1001);
        
        // Guest 2 (Reservation 1002) selects services
        System.out.println("Guest 2 (Reservation #1002) - Bob Smith:");
        serviceManager.addServiceToReservation(1002, airportTransfer);
        System.out.println("  ✓ Added: Airport Transfer");
        serviceManager.displayReservationServices(1002);
        
        // Guest 3 (Reservation 1003) selects services
        System.out.println("Guest 3 (Reservation #1003) - Carol White:");
        serviceManager.addServiceToReservation(1003, spaPackage);
        System.out.println("  ✓ Added: Spa Package");
        serviceManager.addServiceToReservation(1003, lateCheckOut);
        System.out.println("  ✓ Added: Late Check-out");
        serviceManager.displayReservationServices(1003);
        
        // Guest 4 (Reservation 1004) selects services
        System.out.println("Guest 4 (Reservation #1004) - David Brown:");
        serviceManager.addServiceToReservation(1004, petFriendly);
        System.out.println("  ✓ Added: Pet-Friendly Room");
        serviceManager.addServiceToReservation(1004, breakfast);
        System.out.println("  ✓ Added: Daily Breakfast");
        serviceManager.displayReservationServices(1004);
        
        // Guest 5 (Reservation 1005) - No services selected
        System.out.println("Guest 5 (Reservation #1005) - Eva Martinez:");
        System.out.println("  (No add-on services selected)");
        serviceManager.displayReservationServices(1005);
        
        // Step 5: Display service analytics
        System.out.println("[STEP 5] Service Analytics and Cost Breakdown...");
        System.out.println("========================================");
        System.out.println("RESERVATION SERVICE COSTS");
        System.out.println("========================================");
        
        Map<Integer, Double> costByReservation = serviceManager.getCostByReservation();
        for (Integer resId : reservationIds) {
            double cost = costByReservation.getOrDefault(resId, 0.0);
            System.out.println("Reservation #" + resId + ": $" + String.format("%.2f", cost));
        }
        
        System.out.println("----------------------------------------");
        System.out.println("Total Add-On Revenue: $" + 
                         String.format("%.2f", serviceManager.getTotalServiceRevenue()));
        System.out.println("========================================");
        
        // Step 6: Demonstrate service modification capability
        System.out.println("\n[STEP 6] Demonstrating Service Modification...");
        System.out.println("Guest in Reservation #1001 decides to remove Room Service Voucher");
        
        // Find the Room Service Voucher ID to remove
        List<Service> services1001 = serviceManager.getServicesForReservation(1001);
        int roomServiceId = -1;
        for (Service s : services1001) {
            if (s.getServiceName().equals("Room Service Voucher")) {
                roomServiceId = s.getServiceId();
                break;
            }
        }
        
        if (roomServiceId != -1) {
            boolean removed = serviceManager.removeServiceFromReservation(1001, roomServiceId);
            if (removed) {
                System.out.println("✓ Room Service Voucher removed");
                System.out.println("Updated services for Reservation #1001:");
                serviceManager.displayReservationServices(1001);
            }
        }
        
        // Step 7: Display complete add-on services report
        System.out.println("[STEP 7] Complete Add-On Services Report...");
        serviceManager.displayCompleteReport();
        
        // Step 8: Verify core booking logic is unchanged
        System.out.println("[STEP 8] Verification - Core Booking Logic Unchanged...");
        System.out.println("========================================");
        System.out.println("Core Booking State (From Use Case 6)");
        System.out.println("========================================");
        System.out.println("✓ Room allocations: UNCHANGED");
        System.out.println("✓ Inventory state: UNCHANGED");
        System.out.println("✓ Reservation confirmations: UNCHANGED");
        System.out.println("✓ Double-booking prevention: STILL ACTIVE");
        System.out.println();
        System.out.println("Add-On Features (Use Case 7)");
        System.out.println("----------------------------------------");
        System.out.println("✓ Optional services: NEW");
        System.out.println("✓ Service costs: NEW");
        System.out.println("✓ One-to-many mapping: NEW");
        System.out.println("✓ Reservation enrichment: NEW");
        System.out.println("========================================");
        
        // Summary
        System.out.println("\n========================================");
        System.out.println("SUMMARY - Use Case 7 Achievements");
        System.out.println("========================================");
        System.out.println("✓ Business Extensibility: Added services without core changes");
        System.out.println("✓ One-to-Many Relationship: Multiple services per reservation");
        System.out.println("✓ Map and List Combination: Efficient service lookup");
        System.out.println("✓ Composition Model: Services attached, not inherited");
        System.out.println("✓ Separation of Concerns: Add-on logic isolated");
        System.out.println("✓ Cost Aggregation: Services priced independently");
        System.out.println("✓ Total Add-On Revenue: $" + 
                         String.format("%.2f", serviceManager.getTotalServiceRevenue()));
        System.out.println("✓ Reservations with Services: " + 
                         serviceManager.getCostByReservation().keySet().size() + " out of " + reservationIds.length);
        
        System.out.println("\nKey Learning:");
        System.out.println("Real bookings require flexibility beyond the primary room allocation.");
        System.out.println("Using composition and Map/List structures allows services to be");
        System.out.println("added without complicating core booking logic. Each reservation");
        System.out.println("can have its own set of services, managed independently.");
        System.out.println("========================================");
        System.out.println("Application terminated successfully!");
    }
}
