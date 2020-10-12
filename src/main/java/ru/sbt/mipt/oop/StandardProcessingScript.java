package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class StandardProcessingScript implements ProcessingScript {
    private final SmartHome smartHome;
    private final Signalization signalization;

    StandardProcessingScript(SmartHome smartHome, Signalization signalization) {
        this.smartHome = smartHome;
        this.signalization = signalization;
    }

    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
            // событие от источника света
            EventProcessor processor = new LightEventProcessor(smartHome, event);
            processor.processEvent();
        } else if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            // событие от двери
            EventProcessor processor = new DoorEventProcessor(smartHome, event);
            processor.processEvent();

            // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
            // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
            if (event.getType() == DOOR_CLOSED && smartHome.getEntranceDoorsIds().contains(event.getObjectId())) {
                EventProcessor eventProcessor = new HallDoorEventProcessor(smartHome);
                eventProcessor.processEvent();
            }
        } else if (event.getType() == ALARM_ACTIVATE || event.getType() == ALARM_CANCELING || event.getType() == ALARM_DEACTIVATE) {
            EventProcessor processor = new SignalizationEventProcessor(signalization, event);
            processor.processEvent();
        }

    }
}
