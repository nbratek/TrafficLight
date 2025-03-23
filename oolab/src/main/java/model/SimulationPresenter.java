package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.util.List;

public class SimulationPresenter {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private StackPane infoPane;
    @FXML
    private Text infoText;

    private Intersection intersection;


    private Rectangle roadNorth;
    private Rectangle roadSouth;
    private Rectangle roadEast;
    private Rectangle roadWest;

    @FXML
    public void initialize() {
        intersection = App.intersection;

        drawRoads();
        updateRoadColors();
        updateInfo();
        startSimulation();
    }



    private void drawRoads() {
        double width = 800;
        double height = 600;
        double roadWidth = 80;
        double roadLength = 200;

        // (North)
        roadNorth = new Rectangle(roadWidth, roadLength, Color.DARKGRAY);
        roadNorth.setX((width - roadWidth) / 2);
        roadNorth.setY(0);
        roadNorth.setArcWidth(20);
        roadNorth.setArcHeight(20);

        // (South)
        roadSouth = new Rectangle(roadWidth, roadLength, Color.DARKGRAY);
        roadSouth.setX((width - roadWidth) / 2);
        roadSouth.setY(height - roadLength);
        roadSouth.setArcWidth(20);
        roadSouth.setArcHeight(20);

        // (West)
        roadWest = new Rectangle(roadLength, roadWidth, Color.DARKGRAY);
        roadWest.setX(0);
        roadWest.setY((height - roadWidth) / 2);
        roadWest.setArcWidth(20);
        roadWest.setArcHeight(20);

        // (East)
        roadEast = new Rectangle(roadLength, roadWidth, Color.DARKGRAY);
        roadEast.setX(width - roadLength);
        roadEast.setY((height - roadWidth) / 2);
        roadEast.setArcWidth(20);
        roadEast.setArcHeight(20);

        rootPane.getChildren().addAll(roadNorth, roadSouth, roadWest, roadEast);
    }


    private void updateRoadColors() {
        Color activeColor;
        LightsState state = intersection.getCurrentCycleState();
        if (state == LightsState.GREEN) {
            activeColor = Color.LIMEGREEN;
        } else if (state == LightsState.GREEN_ARROW) {
            activeColor = Color.LIGHTGREEN;
        } else if (state == LightsState.YELLOW) {
            activeColor = Color.YELLOW;
        } else {
            activeColor = Color.DARKGRAY;
        }
        Color inactiveColor = Color.DARKRED;
        if (intersection.isPhaseNS()) {
            roadNorth.setFill(activeColor);
            roadSouth.setFill(activeColor);
            roadEast.setFill(inactiveColor);
            roadWest.setFill(inactiveColor);
        } else {
            roadEast.setFill(activeColor);
            roadWest.setFill(activeColor);
            roadNorth.setFill(inactiveColor);
            roadSouth.setFill(inactiveColor);
        }
    }


    private void updateInfo() {
        int northVehicles = intersection.getRoads().get(Directions.north).getTotalVehicles();
        int southVehicles = intersection.getRoads().get(Directions.south).getTotalVehicles();
        int eastVehicles = intersection.getRoads().get(Directions.east).getTotalVehicles();
        int westVehicles = intersection.getRoads().get(Directions.west).getTotalVehicles();
        String activeGroup = intersection.isPhaseNS() ? "NS (North-South)" : "EW (East-West)";
        String cycleState = intersection.getCurrentCycleState().toString();
        String text = String.format(
            "State of the Intersection:\n" +
            "North: %d vehicles\n" +
            "South: %d vehicles\n" +
            "East: %d vehicles\n" +
            "West: %d vehicles\n" +
            "Active group: %s\n" +
            "Cycle status: %s",
            northVehicles, southVehicles, eastVehicles, westVehicles, activeGroup, cycleState
        );
        infoText.setText(text);
    }


    @FXML
    public void startSimulation(){
        TrafficFlow.processCommands("src/main/resources/input.json", "src/main/resources/output.json", intersection);
        updateRoadColors();
        updateInfo();
    }

    @FXML
    public void onStepClick() {
        intersection.processStep();
        updateRoadColors();
        updateInfo();
    }
}
