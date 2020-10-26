package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.CCSensorEvent;

import java.util.Map;

public class EventProcessorAdapter implements com.coolcompany.smarthome.events.EventHandler {
    private final EventHandler eventHandler;
    private final Map<String, SensorEventType> mapOfEvents;
    private final int code;

    public EventProcessorAdapter(EventHandler eventHandler, Map<String, SensorEventType> mapOfEvents, int code) {
        this.eventHandler = eventHandler;
        this.mapOfEvents = mapOfEvents;
        this.code = code;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        SensorEventType type = mapOfEvents.get(event.getEventType());
        SensorEvent sensorEvent;
        if (type == SensorEventType.ALARM_ACTIVATE || type == SensorEventType.ALARM_DEACTIVATE) {
            sensorEvent = new SensorEvent(type, event.getObjectId(), code);
        } else {
            sensorEvent = new SensorEvent(type, event.getObjectId());
        }
        eventHandler.processEvent(sensorEvent);
    }
}
