package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class StandardProcessingScript implements ProcessingScript {

    public void doScript(SmartHome smartHome, Sensor sensor) {
        SensorEvent event = sensor.getNextSensorEvent();
        while (event != null) {
            System.out.println("Got event: " + event);
            if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
                // событие от источника света
                EventProcessor processor = new LightEventProcessor(smartHome, event);
                processor.processEvent();
            }
            if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
                // событие от двери
                EventProcessor processor = new DoorEventProcessor(smartHome, event);
                processor.processEvent();

                // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
                // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
                Room room = smartHome.getRoomByDoor(event.getObjectId());
                if (room != null && event.getType() == DOOR_CLOSED && room.getName().equals("hall")) {
                    EventProcessor eventProcessor = new HallDoorEventProcessor(smartHome);
                    eventProcessor.processEvent();
                }
            }
            event = sensor.getNextSensorEvent();
        }
    }
}
