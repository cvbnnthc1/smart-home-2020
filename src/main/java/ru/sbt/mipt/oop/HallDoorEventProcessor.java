package ru.sbt.mipt.oop;

import java.util.Collection;

public class HallDoorEventProcessor implements EventProcessor {
    private final SmartHome smartHome;

    HallDoorEventProcessor(SmartHome smartHome) {
        if (smartHome == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent() {
        Collection<Room> rooms = smartHome.getRooms();
        for (Room room : rooms) {
            for (Light light : room.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                System.out.println("Pretent we're sending command " + command);
            }
        }
    }
}
