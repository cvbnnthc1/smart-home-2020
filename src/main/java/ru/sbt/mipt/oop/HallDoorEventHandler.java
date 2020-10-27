package ru.sbt.mipt.oop;

public class HallDoorEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    HallDoorEventHandler(SmartHome smartHome, CommandSender commandSender) {
        if (smartHome == null || commandSender == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
        this.commandSender = commandSender;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (event.getType() == SensorEventType.DOOR_CLOSED) {
            boolean isCloseHallDoor = smartHome.execute(s -> {
                if (s instanceof Door) {
                    Door door = (Door) s;
                    if (door.getId().equals(event.getObjectId())
                            && door.getRoomName().equals("hall")) {
                        return true;
                    }
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