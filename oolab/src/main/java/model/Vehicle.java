package model;

public class Vehicle {
    private String id;
    private Directions startRoad;
    private Directions endRoad;

    public Vehicle(String id, String startRoad, String endRoad) {
        this.id = id;
        this.startRoad = Directions.valueOf(startRoad.toLowerCase());
        this.endRoad = Directions.valueOf(endRoad.toLowerCase());
    }

    public String getId() {
        return id;
    }

    public Directions getStartRoad() {
        return startRoad;
    }

    public Directions getEndRoad() {
        return endRoad;
    }
}
