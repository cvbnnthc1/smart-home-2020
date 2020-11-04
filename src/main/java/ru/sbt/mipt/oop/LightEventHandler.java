package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class LightEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    LightEventHandler(SmartHome smartHome, CommandSender commandSender) {
        this.commandSender = commandSender;
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
        } else if (event.getType() == ALL_LIGHTS_OFF) {
            smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    light.setOn(false);
                    SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                    commandSender.sendCommand(command);
                    return true;
                }
                return false;
            });
        }  else if (event.getType() == ALL_LIGHTS_ON) {
            smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    light.setOn(true);
                    SensorCommand command = new SensorCommand(CommandType.LIGHT_ON, light.getId());
                    commandSender.sendCommand(command);
                    return true;
                }
                return false;
            });
        } else if (event.getType() == LIGHT_ON_IN_HALL) {
            smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    if (light.getRoomName().equals("hall")) {
                        light.setOn(true);
                        SensorCommand command = new SensorCommand(CommandType.LIGHT_ON, light.getId());
                        commandSender.sendCommand(command);
                        return true;
                    }
                }
                return false;
            });
        }
    }
}