package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class StandardEventProcessor implements EventProcessor{

    public void processEvent(SmartHome smartHome, SensorEvent event) {
        System.out.println("Got event: " + event);
        if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
            // событие от источника света
            Room room = smartHome.getRoomByLight(event.getObjectId());
            if (room != null) {
                Light light = room.getLight(event.getObjectId());
                if (event.getType() == LIGHT_ON) {
                    Action action = new LightOff(light, room);
                    action.act();
                } else {
                    Action action = new LightOn(light, room);
                    action.act();
                }
            }
        }
        if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            // событие от двери
            Room room = smartHome.getRoomByDoor(event.getObjectId());
            if (room != null) {
                Door door = room.getDoor(event.getObjectId());
                if (event.getType() == DOOR_OPEN) {
                    Action action = new DoorOpen(door, room);
                    action.act();
                } else {
                    // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
                    // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
                    if (room.getName().equals("hall")) {
                        Action action = new CloseAllDoors(smartHome.getRooms());
                        action.act();
                    } else {
                        Action action = new DoorClose(door, room);
                        action.act();
                    }
                }
            }
        }
    }
}
