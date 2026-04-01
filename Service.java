/**
 * Service Class - Represents an optional add-on service for reservations
 * 
 * This class encapsulates all information about an optional service offering
 * that guests can add to their reservations. Services are composed with
 * reservations rather than inherited, allowing flexible feature addition.
 * 
 * Key Concepts:
 * - Immutable Service Definition: Once created, service details don't change
 * - Service Identification: Each service has a unique ID
 * - Cost Encapsulation: Service cost is encapsulated within the service
 * - Composition: Services are attached to reservations, not part of inheritance
 * - Extensibility: New service types can be added independently
 * 
 * Service Types:
 * - Early Check-in: Allows early access to room
 * - Late Check-out: Extends stay duration
 * - Airport Transfer: Transportation service
 * - Room Service: In-room dining
 * - Spa Package: Wellness services
 * - Pet Friendly: Allow pets in room
 * - Breakfast: Complimentary or upgraded breakfast
 * 
 * @author Hotel Booking Team
 * @version 7.0
 * @since 2026-04-01
 */
public class Service {
    
    // Static counter for generating unique service IDs
    private static int serviceCounter = 2000;
    
    // Service attributes
    private int serviceId;
    private String serviceName;
    private String serviceDescription;
    private double serviceCost;
    private String serviceCategory;
    
    /**
     * Constructor - Creates a new optional service
     * 
     * @param serviceName Name of the service (e.g., "Early Check-in")
     * @param serviceDescription Description of what the service offers
     * @param serviceCost Cost of the service
     * @param serviceCategory Category of the service (e.g., "Convenience", "Dining")
     */
    public Service(String serviceName, String serviceDescription, 
                   double serviceCost, String serviceCategory) {
        this.serviceId = ++serviceCounter;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.serviceCost = serviceCost;
        this.serviceCategory = serviceCategory;
    }
    
    /**
     * Get the service ID
     * 
     * @return Unique identifier for this service
     */
    public int getServiceId() {
        return serviceId;
    }
    
    /**
     * Get the service name
     * 
     * @return Name of the service
     */
    public String getServiceName() {
        return serviceName;
    }
    
    /**
     * Get the service description
     * 
     * @return Description of what the service offers
     */
    public String getServiceDescription() {
        return serviceDescription;
    }
    
    /**
     * Get the service cost
     * 
     * @return Cost in currency units
     */
    public double getServiceCost() {
        return serviceCost;
    }
    
    /**
     * Get the service category
     * 
     * @return Category of the service
     */
    public String getServiceCategory() {
        return serviceCategory;
    }
    
    /**
     * Display service details
     */
    public void displayServiceDetails() {
        System.out.println("Service ID: #" + serviceId);
        System.out.println("Name: " + serviceName);
        System.out.println("Category: " + serviceCategory);
        System.out.println("Cost: $" + String.format("%.2f", serviceCost));
        System.out.println("Description: " + serviceDescription);
    }
    
    @Override
    public String toString() {
        return serviceName + " - $" + String.format("%.2f", serviceCost);
    }
}
