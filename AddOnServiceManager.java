import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AddOnServiceManager Class - Manages optional services for reservations
 * 
 * This class manages the association between reservations and optional add-on services.
 * It uses a Map<Integer, List<Service>> structure to support one-to-many relationships
 * where a single reservation can have multiple associated services.
 * 
 * Key Concepts:
 * - One-to-Many Relationship: One reservation can have many services
 * - Map and List Combination: HashMap maps reservation ID to List of services
 * - Composition: Services are composed with reservations, not inherited
 * - Separation of Concerns: Add-on logic separated from core booking
 * - Cost Aggregation: Service costs calculated without modifying inventory
 * - Business Extensibility: New services can be added without core changes
 * 
 * Data Structure:
 * Map<Integer, List<Service>>
 * - Key: Reservation ID
 * - Value: List of services attached to that reservation
 * 
 * Example:
 * 1001 -> [Early Check-in, Room Service, Spa Package]
 * 1002 -> [Airport Transfer, Breakfast]
 * 1003 -> [Pet Friendly]
 * 
 * Benefits:
 * - Flexible service attachment
 * - Easy lookup of services for a reservation
 * - Clean separation from core booking logic
 * - Supports unlimited services per reservation
 * - No modification to room allocation or inventory
 * 
 * @author Hotel Booking Team
 * @version 7.0
 * @since 2026-04-01
 */
public class AddOnServiceManager {
    
    // Map from reservation ID to list of selected services
    // This enables efficient lookup and management of services per reservation
    private Map<Integer, List<Service>> reservationServices;
    
    // Track total cost collected from all services
    private double totalServiceRevenue;
    
    /**
     * Constructor - Initializes an empty service management system
     */
    public AddOnServiceManager() {
        this.reservationServices = new HashMap<>();
        this.totalServiceRevenue = 0.0;
    }
    
    /**
     * Add a service to a reservation
     * Multiple services can be added to the same reservation.
     * 
     * @param reservationId The reservation ID
     * @param service The service to add
     * @return true if service was added successfully
     */
    public boolean addServiceToReservation(int reservationId, Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        
        // Create service list for this reservation if it doesn't exist
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        
        List<Service> services = reservationServices.get(reservationId);
        
        // Check if this service is already added (prevent duplicates)
        for (Service existingService : services) {
            if (existingService.getServiceId() == service.getServiceId()) {
                return false; // Service already added
            }
        }
        
        // Add the service
        services.add(service);
        totalServiceRevenue += service.getServiceCost();
        return true;
    }
    
    /**
     * Remove a service from a reservation
     * 
     * @param reservationId The reservation ID
     * @param serviceId The service ID to remove
     * @return true if service was removed, false if not found
     */
    public boolean removeServiceFromReservation(int reservationId, int serviceId) {
        if (!reservationServices.containsKey(reservationId)) {
            return false; // Reservation not found
        }
        
        List<Service> services = reservationServices.get(reservationId);
        
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getServiceId() == serviceId) {
                Service removed = services.remove(i);
                totalServiceRevenue -= removed.getServiceCost();
                
                // Remove reservation entry if no services remain
                if (services.isEmpty()) {
                    reservationServices.remove(reservationId);
                }
                return true;
            }
        }
        return false; // Service not found
    }
    
    /**
     * Get all services for a reservation
     * 
     * @param reservationId The reservation ID
     * @return List of services, or empty list if no services
     */
    public List<Service> getServicesForReservation(int reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }
    
    /**
     * Get the number of services for a reservation
     * 
     * @param reservationId The reservation ID
     * @return Number of services attached to this reservation
     */
    public int getServiceCount(int reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>()).size();
    }
    
    /**
     * Calculate total cost for services on a reservation
     * 
     * @param reservationId The reservation ID
     * @return Total cost of all services
     */
    public double calculateServiceCost(int reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        double totalCost = 0.0;
        
        for (Service service : services) {
            totalCost += service.getServiceCost();
        }
        
        return totalCost;
    }
    
    /**
     * Check if a reservation has any services
     * 
     * @param reservationId The reservation ID
     * @return true if reservation has at least one service
     */
    public boolean hasServices(int reservationId) {
        return reservationServices.containsKey(reservationId) && 
               !reservationServices.get(reservationId).isEmpty();
    }
    
    /**
     * Get total revenue from all services
     * 
     * @return Total cost of all services across all reservations
     */
    public double getTotalServiceRevenue() {
        return totalServiceRevenue;
    }
    
    /**
     * Get the cost breakdown by reservation
     * 
     * @return Map of reservation ID to total service cost
     */
    public Map<Integer, Double> getCostByReservation() {
        Map<Integer, Double> costs = new HashMap<>();
        
        for (Map.Entry<Integer, List<Service>> entry : reservationServices.entrySet()) {
            double cost = 0.0;
            for (Service service : entry.getValue()) {
                cost += service.getServiceCost();
            }
            costs.put(entry.getKey(), cost);
        }
        
        return costs;
    }
    
    /**
     * Display all services for a reservation
     * 
     * @param reservationId The reservation ID
     */
    public void displayReservationServices(int reservationId) {
        System.out.println("Services for Reservation #" + reservationId + ":");
        System.out.println("-".repeat(40));
        
        List<Service> services = getServicesForReservation(reservationId);
        
        if (services.isEmpty()) {
            System.out.println("No add-on services selected");
        } else {
            int count = 1;
            double totalCost = 0.0;
            
            for (Service service : services) {
                System.out.println(count + ". " + service.getServiceName() + 
                                 " ($" + String.format("%.2f", service.getServiceCost()) + ")");
                System.out.println("   " + service.getServiceDescription());
                totalCost += service.getServiceCost();
                count++;
            }
            
            System.out.println("-".repeat(40));
            System.out.println("Total Add-On Cost: $" + String.format("%.2f", totalCost));
        }
        System.out.println();
    }
    
    /**
     * Display complete add-on services report
     */
    public void displayCompleteReport() {
        System.out.println("\n========================================");
        System.out.println("ADD-ON SERVICES REPORT");
        System.out.println("========================================");
        
        if (reservationServices.isEmpty()) {
            System.out.println("No add-on services have been selected.");
        } else {
            for (Integer reservationId : reservationServices.keySet()) {
                List<Service> services = reservationServices.get(reservationId);
                double cost = calculateServiceCost(reservationId);
                
                System.out.println("\nReservation #" + reservationId);
                System.out.println("Services: " + services.size());
                
                for (Service service : services) {
                    System.out.println("  • " + service.getServiceName() + " - $" + 
                                     String.format("%.2f", service.getServiceCost()));
                }
                
                System.out.println("Subtotal: $" + String.format("%.2f", cost));
            }
        }
        
        System.out.println("\n----------------------------------------");
        System.out.println("Total Add-On Revenue: $" + String.format("%.2f", totalServiceRevenue));
        System.out.println("========================================\n");
    }
}
