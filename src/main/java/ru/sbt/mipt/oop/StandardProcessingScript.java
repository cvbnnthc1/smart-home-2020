package ru.sbt.mipt.oop;

import java.util.Collection;

public class StandardProcessingScript implements ProcessingScript {
    private final Collection<EventProcessor> processors;

    public StandardProcessingScript(Collection<EventProcessor> processors) {
        this.processors = processors;
    }

    public void executeScript(SmartHome smartHome, SensorEventProvider sensorEventProvider) {
        SensorEvent event = sensorEventProvider.getNextSensorEvent();
        while (event != null) {
            System.out.println("Got event: " + event);
            for (EventProcessor processor: processors) {
                processor.processEvent(event);
            }
            event = sensorEventProvider.getNextSensorEvent();
        }
    }
}