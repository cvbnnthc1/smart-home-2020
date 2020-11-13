package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements EventHandler {
    private final SmartHome smartHome;

    DoorEventHandler(SmartHome smartHome) {
        if (smartHome == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (event.getType() == DOOR_OPEN) {
            smartHome.execute(s -> {
                if (s instanceof Door) {
                    Door door = (Door) s;
                    if (door.getId().equals(event.getObjectId())) {
                        door.setOpen(true);
                        System.out.println("Door " + door.getId() + " was opened.");
                        return true;
                    }
                }
                return false;
            });
        } else if (event.getType() == DOOR_CLOSED) {
            smartHome.execute(s -> {
                if (s instanceof Door) {
                    Door door = (Door) s;
                    if (door.getId().equals(event.getObjectId())) {
                        door.setOpen(false);
                        System.out.println("Door " + door.getId() + " was closed.");
                        return true;
                    }
                }
                return false;
            });
        }
    }
}