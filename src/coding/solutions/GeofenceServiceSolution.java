package coding.solutions;

import coding.GeofenceService.Vehicle;
import coding.GeofenceService.CircularGeofence;

import java.util.List;
import java.util.ArrayList;

public class GeofenceServiceSolution {

    public static List<Vehicle> findVehiclesOnBoundary(List<Vehicle> vehicles, List<CircularGeofence> geofences, double epsilon) {
        List<Vehicle> boundaryVehicles = new ArrayList<>();
        if (vehicles == null || geofences == null) {
            return boundaryVehicles;
        }

        for (Vehicle vehicle : vehicles) {
            for (CircularGeofence geofence : geofences) {
                double dx = vehicle.x() - geofence.centerX();
                double dy = vehicle.y() - geofence.centerY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                
                if (Math.abs(distance - geofence.radius()) <= epsilon) {
                    boundaryVehicles.add(vehicle);
                    break; // Found on at least one boundary, move to the next vehicle
                }
            }
        }
        
        return boundaryVehicles;
    }
}
