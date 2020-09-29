package ru.sbt.mipt.oop;

import java.util.Collection;

public class CloseAllDoors implements Action{
    private final Collection<Room> rooms;

    CloseAllDoors(Collection<Room> rooms) {
        if (rooms == null) throw new IllegalArgumentException("Null input");
        this.rooms = rooms;
    }

    public void act() {
        for (Room room : rooms) {
            for (Light light : room.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                System.out.println("Pretent we're sending command " + command);
            }
        }
    }
}
