# TrafficLight
Traffic Light Simulation

## Description
Based on what the traffic lights are showing, which can either be for North-South (NS) or East-West (EW) traffic, the algorithm figures out which roads are active. Depending on the light's color, the traffic behaves differently: when the light is green, cars can go straight or turn right. If it switches to a green arrow, cars can make a left turn. When it turns yellow, all cars have to stop. After each step of the simulation, the algorithm updates the cycle of the lights, moving from green, to green arrow, then to yellow, before it resets the lights back to green and switches the focus between NS and EW roads.

## Running the simulation
./gradlew run --args="src/main/resources/input.json src/main/resources/output.json"
