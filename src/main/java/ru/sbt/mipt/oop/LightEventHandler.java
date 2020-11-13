package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;
import static ru.sbt.mipt.oop.SensorEventType.LIGHT_OFF;

public class LightEventHandler implements EventHandler {
    private final SmartHome smartHome;

    LightEventHandler(SmartHome smartHome) {
        if (smartHome == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (event.getType() == LIGHT_ON) {
            smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    if (light.getId().equals(event.getObjectId())) {
                        light.setOn(true);
                        System.out.println("Light " + light.getId() + " was on.");
                        return true;
                    }
                }
                return false;
            });
        } else if (event.getType() == LIGHT_OFF) {
            smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    if (light.getId().equals(event.getObjectId())) {
                        light.setOn(false);
                        System.out.println("Light " + light.getId() + " was off.");
                        return true;
                    }
                }
                return false;
            });
        }
    }
}