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

    @Override
    public void processEvent() {
        if (event.getType() == DOOR_OPEN) {
            Action doorOpen = new Action(s -> {
                if (s instanceof Door && s.getId().equals(event.getObjectId())) {
                    Door door = (Door) s;
                    door.setOpen(true);
                    System.out.println("Door " + door.getId() + " was opened.");
                }
                return null;
            });
            smartHome.execute(doorOpen);
        } else {
            Action doorClose = new Action(s -> {
                if (s instanceof Door && s.getId().equals(event.getObjectId())) {
                    Door door = (Door) s;
                    door.setOpen(false);
                    System.out.println("Door " + door.getId() + " was closed.");
                }
                return null;
            });
            smartHome.execute(doorClose);
        }
    }
}
