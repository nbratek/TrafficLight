package model;

import java.util.List;

public class CommandContainer {
    private List<Command> commands;

    public List<Command> getCommands() {
        return commands;
    }
    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public static class Command {
        private CommandType type;
        private String vehicleId;
        private String startRoad;
        private String endRoad;

        public CommandType getType() {
            return type;
        }
        public String getVehicleId() {
            return vehicleId;
        }
        public String getStartRoad() {
            return startRoad;
        }
        public String getEndRoad() {
            return endRoad;
        }

    }

    public static class AddVehicleCommand extends Command {
    }

    public static class StepCommand extends Command {
    }
}
