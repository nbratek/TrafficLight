package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.*;

public class Intersection {
    private Map<Directions, Road> roads;
    private boolean phaseNS; // true: North-South active, false: East-West
    private LightsState currentCycleState; // active group status: GREEN, GREEN_ARROW, YELLOW
    private int cycleTick; // counter in a cycle state
    private List<Map<String, List<String>>> stepStatuses;

    public Intersection() {
        roads = new EnumMap<>(Directions.class);
        for (Directions d : Directions.values()) {
            roads.put(d, new Road(d));
        }
        // at the beginning active NS group
        phaseNS = true;
        currentCycleState = LightsState.GREEN;
        cycleTick = 0;
        stepStatuses = new ArrayList<>();
    }

    public Map<Directions, Road> getRoads() {
        return roads;
    }

    public boolean isPhaseNS() {
        return phaseNS;
    }

    public LightsState getCurrentCycleState() {
        return currentCycleState;
    }

    public void addVehicle(String vehicleId, String startRoad, String endRoad) {
        Vehicle vehicle = new Vehicle(vehicleId, startRoad, endRoad);
        Directions dir = Directions.valueOf(startRoad.toLowerCase());
        Road road = roads.get(dir);
        if (road != null) {
            road.addVehicle(vehicle);
            System.out.println("Added vehicle " + vehicleId + " on road " + dir);
        } else {
            System.out.println("The road " + startRoad + " does not exist.");
        }
    }

    /**
     * For the active group of roads (NS or EW) it allows vehicles to pass according to the current state:
     * - GREEN: przepuszcza pojazdy z pasa jazdy prosto (lane 1) i skrętu w prawo (lane 2)
     * - GREEN: lets vehicles pass from the straight driving lane (lane 1) and the right-turn lane(lane 2)
     * - GREEN_ARROW: lets vehicles pass from the left-turn lane (lane 0)
     * - YELLOW: does not allow vehicles to pass
     * Then updates the traffic light cycle
     */

    public void processStep() {
        List<String> leftVehicles = new ArrayList<>();
        Directions[] activeGroup;
        if (phaseNS) {
            activeGroup = new Directions[]{Directions.north, Directions.south};
        } else {
            activeGroup = new Directions[]{Directions.east, Directions.west};
        }

        List<Integer> lanesToProcess = new ArrayList<>();
        if (currentCycleState == LightsState.GREEN) {
            lanesToProcess = Arrays.asList(1, 2); // straight driving and right turn
        } else if (currentCycleState == LightsState.GREEN_ARROW) {
            lanesToProcess = Arrays.asList(0);    // left turn
        } else if (currentCycleState == LightsState.YELLOW) {
            lanesToProcess = Collections.emptyList(); // no passage
        }

        for (Directions d : activeGroup) {
            Road r = roads.get(d);
            if (r != null) {
                List<Vehicle> removed = r.removeVehiclesFromLanes(lanesToProcess);
                for (Vehicle v : removed) {
                    leftVehicles.add(v.getId());
                }
            }
        }

        Map<String, List<String>> stepResult = new HashMap<>();
        stepResult.put("leftVehicles", leftVehicles);
        stepStatuses.add(stepResult);

        updateCycle();
    }

    /**
     *Updates the light cycle for the active group
     * - GREEN takes 3 steps,
     * - GREEN_ARROW takes 1 step,
     * - YELLOW takes 1 step,
     * After YELLOW is finished, the group switches (NS ⇆ EW) and the cycle resets to GREEN.
     */

    private void updateCycle() {
        if (currentCycleState == LightsState.GREEN) {
            cycleTick++;
            if (cycleTick >= 3) {
                currentCycleState = LightsState.GREEN_ARROW;
                cycleTick = 0;
            }
        } else if (currentCycleState == LightsState.GREEN_ARROW) {
            cycleTick++;
            if (cycleTick >= 1) {
                currentCycleState = LightsState.YELLOW;
                cycleTick = 0;
            }
        } else if (currentCycleState == LightsState.YELLOW) {
            cycleTick++;
            if (cycleTick >= 1) {
                phaseNS = !phaseNS;
                currentCycleState = LightsState.GREEN;
                cycleTick = 0;
            }
        }
        System.out.println("Aktualna grupa: " + (phaseNS ? "NS" : "EW") + ", stan cyklu: " + currentCycleState);
    }

    public void writeOutput(String outputFile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Map<String, Object> output = new HashMap<>();
            output.put("stepStatuses", stepStatuses);
            mapper.writeValue(new File(outputFile), output);
            System.out.println("Wyniki symulacji zapisane do " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
