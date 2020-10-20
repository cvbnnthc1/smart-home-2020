package ru.sbt.mipt.oop;

import java.util.Collection;

public class StandardProcessingScript implements ProcessingScript {
    private final SmartHome smartHome;
    private final Signalization signalization;
    private final Collection<EventProcessor> processors;

    StandardProcessingScript(SmartHome smartHome, Signalization signalization, Collection<EventProcessor> processors) {
        this.smartHome = smartHome;
        this.signalization = signalization;
        this.processors = processors;
    }

    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        for (EventProcessor processor: processors) {
            processor.processEvent(event);
        }

    }
}
