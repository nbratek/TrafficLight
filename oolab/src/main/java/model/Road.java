package model;

import java.util.ArrayList;
import java.util.List;

public class Road {
    private Directions direction;
    private List<Lane> lanes;
    private TrafficLight trafficLight;

    /**
     * The constructor creates 4 lanes:
     *  - Lane 0: for left turns,
     *  -  Lane 1: for straight ahead,
     *  - Lane 2: for right turns,
     *  - Lane 3: incoming/reserve lane.
     */

    public Road(Directions direction) {
        this.direction = direction;
        lanes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lanes.add(new Lane());
        }
        trafficLight = new TrafficLight();
    }



    public void addVehicle(Vehicle vehicle) {
        int laneIndex = determineLane(vehicle);
        if (laneIndex < lanes.size()) {
            lanes.get(laneIndex).addVehicle(vehicle);
        } else {
            lanes.get(3).addVehicle(vehicle);
        }
    }

    /**
     * Specifies which lane to add the vehicle to:
     * - If the vehicle is going straight (target direction is opposite)→ lane 1,
     * - If it is turning right → lane 2,
     * - If it is turning left → lane 0,
     * - In other cases → lane 3.
     */

    private int determineLane(Vehicle vehicle) {
        Directions start = vehicle.getStartRoad();
        Directions dest = vehicle.getEndRoad();
        int startOrd = start.ordinal();
        int destOrd = dest.ordinal();

        if (destOrd == (startOrd + 2) % 4) {
            return 1;
        } else if (destOrd == (startOrd + 1) % 4) {
            return 2;
        } else if (destOrd == (startOrd + 3) % 4) {
            return 0;
        } else {
            return 3;
        }
    }


    public List<Vehicle> removeVehiclesFromLanes(List<Integer> laneIndices) {
        List<Vehicle> removed = new ArrayList<>();
        for (int idx : laneIndices) {
            if (idx < lanes.size() && lanes.get(idx).hasVehicle()) {
                removed.add(lanes.get(idx).removeVehicle());
                System.out.println("Vehicle removed from Lane" + laneIndices.toString());
            }
        }
        return removed;
    }

    public int getTotalVehicles() {
        return lanes.stream().mapToInt(Lane::getVehicleCount).sum();
    }


}
