package coding;

import java.util.List;
import java.util.ArrayList;

/**
 * PROBLEM 1: Geofence Boundary Detection
 * 
 * Given a list of vehicles and a list of active circular geofences, find all vehicle coordinates
 * that lie exactly on the boundary (circumference) of any active geofence zone.
 * 
 * Due to double-precision floating point arithmetic, you should check if the absolute difference
 * between the distance from the vehicle to the center and the radius is within a small delta (epsilon).
 * 
 * E.g., |Distance(Vehicle, Center) - Radius| <= Epsilon
 */
public class GeofenceService {

    public record Vehicle(String id, double x, double y) {}
    
    public record CircularGeofence(String id, double centerX, double centerY, double radius) {}

    /**
     * Finds vehicles that lie on the boundary of any active geofence.
     * 
     * @param vehicles  List of vehicles with coordinates
     * @param geofences List of circular geofences with center coordinates and radius
     * @param epsilon   Tolerance threshold for floating-point calculations
     * @return List of vehicles whose coordinates are on the circumference of a geofence
     */
    public static List<Vehicle> findVehiclesOnBoundary(List<Vehicle> vehicles, List<CircularGeofence> geofences, double epsilon) {
        List<Vehicle> boundaryVehicles = new ArrayList<>();
        
        // TODO: Implement this method.
        // For each vehicle, check if its distance to the center of any geofence is approximately equal to the radius.
        // Distance formula: sqrt((x2 - x1)^2 + (y2 - y1)^2)
        
        return boundaryVehicles;
    }
}
