package model;

public class TrafficLight {
    private LightsState state;

    public TrafficLight() {
        state = LightsState.RED;
    }

    public LightsState getState() {
        return state;
    }

    public void setState(LightsState state) {
        this.state = state;
    }
}
