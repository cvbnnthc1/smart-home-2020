package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class DoorEventProcessor implements EventProcessor {
    private final SmartHome smartHome;
    private final SensorEvent event;

    DoorEventProcessor(SmartHome smartHome, SensorEvent event) {
        if (smartHome == null || event == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
        this.event = event;
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
    public void processEvent() {
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
