package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.CommandContainer;
import model.CommandContainer.Command;

import java.io.File;
import java.util.List;

public class TrafficFlow {
    public static void processCommands(String inputFile, String outputFile, Intersection intersection) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CommandContainer container = mapper.readValue(new File(inputFile), CommandContainer.class);
            List<Command> commands = container.getCommands();
            for (Command cmd : commands) {
                if (cmd.getType() == CommandType.addVehicle) {
                    intersection.addVehicle(cmd.getVehicleId(), cmd.getStartRoad(), cmd.getEndRoad());
                } else if (cmd.getType() == CommandType.step) {
                    intersection.processStep();
                }
            }
            intersection.writeOutput(outputFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
