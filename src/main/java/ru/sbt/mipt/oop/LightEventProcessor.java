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

    @Override
    public void processEvent() {
        if (event.getType() == LIGHT_ON) {
            Action lightOn = new Action(s -> {
                if (s instanceof Light && s.getId().equals(event.getObjectId())) {
                    Light light = (Light) s;
                    light.setOn(true);
                    System.out.println("Light " + light.getId() + " was on.");
                }
                return null;
            });
            smartHome.execute(lightOn);
        } else {
            Action lightOff = new Action(s -> {
                if (s instanceof Light && s.getId().equals(event.getObjectId())) {
                    Light light = (Light) s;
                    light.setOn(false);
                    System.out.println("Light " + light.getId() + " was off.");
                }
                return null;
            });
            smartHome.execute(lightOff);
        }
    }
}