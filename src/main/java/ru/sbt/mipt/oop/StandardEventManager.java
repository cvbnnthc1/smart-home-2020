package ru.sbt.mipt.oop;

import java.util.Collection;

public class StandardEventManager implements EventManager {
    private final Collection<EventHandler> processors;
    private final SensorEventProvider eventProvider;

    StandardEventManager(Collection<EventHandler> processors, SensorEventProvider eventProvider) {
        this.processors = processors;
        this.eventProvider = eventProvider;
    }

    @Override
    public void start() {
        SensorEvent event = eventProvider.getNextSensorEvent();
        while (event != null) {
            System.out.println("Got event: " + event);
            for (EventHandler processor: processors) {
                processor.processEvent(event);
            }
            event = eventProvider.getNextSensorEvent();
        }
    }
}