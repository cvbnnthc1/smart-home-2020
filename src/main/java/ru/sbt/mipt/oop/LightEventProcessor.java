package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;
import static ru.sbt.mipt.oop.SensorEventType.LIGHT_OFF;

public class LightEventProcessor implements EventProcessor {
    private final SmartHome smartHome;

    LightEventProcessor(SmartHome smartHome) {
        if (smartHome == null) throw new IllegalArgumentException("Null input");
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (event.getType() == LIGHT_ON) {
            smartHome.execute(s -> {
                if (s instanceof Light && s.getId().equals(event.getObjectId())) {
                    Light light = (Light) s;
                    light.setOn(true);
                    System.out.println("Light " + light.getId() + " was on.");
                    return true;
                }
                return false;
            });
        } else if (event.getType() == LIGHT_OFF) {
            smartHome.execute(s -> {
                if (s instanceof Light && s.getId().equals(event.getObjectId())) {
                    Light light = (Light) s;
                    light.setOn(false);
                    System.out.println("Light " + light.getId() + " was off.");
                    return true;
                }
                return false;
            });
        }
    }
}