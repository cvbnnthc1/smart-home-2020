package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;

public class LightEventProcessor implements EventProcessor {
    private final SmartHome smartHome;
    private final SensorEvent event;

    LightEventProcessor(SmartHome smartHome, SensorEvent event) {
        if (smartHome == null || event == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
        this.event = event;
    }

    private void lightOn(Light light, Room room) {
        light.setOn(true);
        System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
    }

    public void lightOff(Light light, Room room) {
        light.setOn(false);
        System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
    }

    @Override
    public void processEvent() {
        Room room = smartHome.getRoomByLight(event.getObjectId());
        if (room != null) {
            Light light = room.getLight(event.getObjectId());
            if (event.getType() == LIGHT_ON) {
                lightOn(light, room);
            } else {
                lightOff(light, room);
            }
        }
    }
}