package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class DoorEventProcessor implements EventProcessor {
    private final SmartHome smartHome;

    DoorEventProcessor(SmartHome smartHome) {
        if (smartHome == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
    }

    private void doorOpen(Door door, Room room) {
        door.setOpen(true);
        System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
    }

    private void doorCLose(Door door, Room room) {
        door.setOpen(false);
        System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            Room room = smartHome.getRoomByDoor(event.getObjectId());
            if (room != null) {
                Door door = room.getDoor(event.getObjectId());
                if (event.getType() == DOOR_OPEN) {
                    doorOpen(door, room);
                } else {
                    doorCLose(door, room);
                }
            }
        }
    }
}
