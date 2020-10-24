package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;
import static ru.sbt.mipt.oop.SensorEventType.LIGHT_OFF;

public class LightEventProcessor implements EventProcessor {
    private final SmartHome smartHome;

    LightEventProcessor(SmartHome smartHome) {
        if (smartHome == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
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
    public void processEvent(SensorEvent event) {
        if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
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
}