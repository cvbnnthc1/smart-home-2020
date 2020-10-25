package ru.sbt.mipt.oop;

import java.util.Collection;

public class StandardProcessingScript implements ProcessingScript {
    private final Collection<EventProcessor> processors;

    StandardProcessingScript(Collection<EventProcessor> processors) {
        this.processors = processors;
    }

    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        for (EventProcessor processor: processors) {
            processor.processEvent(event);
        }

    }
}