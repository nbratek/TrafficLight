package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static Intersection intersection = new Intersection();

    public static void main(String[] args) {

        if(args.length >= 2) {
            String inputFile = args[0];
            String outputFile = args[1];
            Platform.runLater(()->TrafficFlow.processCommands(inputFile, outputFile, intersection));
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/simulation.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Traffic Light Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
