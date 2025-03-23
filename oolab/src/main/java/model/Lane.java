package model;

import java.util.LinkedList;
import java.util.Queue;

public class Lane {
    private Queue<Vehicle> vehicles;

    public Lane() {
        vehicles = new LinkedList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.offer(vehicle);
    }

    public boolean hasVehicle() {
        return !vehicles.isEmpty();
    }

    public Vehicle removeVehicle() {
        return vehicles.poll();
    }

    public int getVehicleCount() {
        return vehicles.size();
    }
}
