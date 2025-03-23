import model.Directions;
import model.Intersection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {



    @Test
    void testProcessStep() {
        Intersection intersection = new Intersection();

        intersection.addVehicle("vehicle1", "NORTH", "SOUTH");
        intersection.addVehicle("vehicle2", "SOUTH", "NORTH");
        intersection.processStep();
        assertEquals(0, intersection.getRoads().get(Directions.north).getTotalVehicles(), "There should be no vehicles on the north road");
        assertEquals(0, intersection.getRoads().get(Directions.south).getTotalVehicles(), "There should be no vehicles on the south road");
        intersection.addVehicle("vehicle3", "EAST", "WEST");
        intersection.addVehicle("vehicle4", "WEST", "EAST");
        intersection.processStep();
        assertEquals(1, intersection.getRoads().get(Directions.east).getTotalVehicles(), "There should be one vehicle on the east road");
        assertEquals(1, intersection.getRoads().get(Directions.west).getTotalVehicles(), "There should be one vehicle on the west road");
        intersection.processStep();
        assertEquals(1, intersection.getRoads().get(Directions.east).getTotalVehicles(), "Vehicles on the eastern road should remain");
        assertEquals(1, intersection.getRoads().get(Directions.west).getTotalVehicles(), "Vehicles on the west road should remain");
    }



}