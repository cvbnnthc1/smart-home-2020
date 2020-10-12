package ru.sbt.mipt.oop;

public class SignalizationEventProcessor implements EventProcessor {
    public final Signalization signalization;
    public final SensorEvent event;

    SignalizationEventProcessor(Signalization signalization, SensorEvent event) {
        if (signalization == null || event == null) throw new IllegalArgumentException("Null input");
        this.signalization = signalization;
        this.event = event;
    }

    @Override
    public void processEvent() {
        signalization.execute(event);
    }
}
