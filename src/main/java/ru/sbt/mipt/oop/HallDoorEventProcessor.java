package ru.sbt.mipt.oop;

public class HallDoorEventProcessor implements EventProcessor {
    private final SmartHome smartHome;

    HallDoorEventProcessor(SmartHome smartHome) {
        if (smartHome == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (event.getType() == SensorEventType.DOOR_CLOSED) {
            boolean isCloseHallDoor = smartHome.execute(s -> {
                if (s instanceof Door && s.getId().equals(event.getObjectId())
                        && (((Door) s).getRoomName().equals("hall"))) {
                    return true;
                }
                return false;
            });
            if (isCloseHallDoor) {
                smartHome.execute(s -> {
                    if (s instanceof Light) {
                        Light light = (Light) s;
                        light.setOn(false);
                        SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                        System.out.println("Pretent we're sending command " + command);
                        return true;
                    }
                    return false;
                });
            }
        }
    }
}
