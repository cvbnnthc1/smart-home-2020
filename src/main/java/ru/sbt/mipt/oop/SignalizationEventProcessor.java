package ru.sbt.mipt.oop;

public class SignalizationEventProcessor implements EventProcessor {
    public final Signalization signalization;

    SignalizationEventProcessor(Signalization signalization) {
        if (signalization == null) throw new IllegalArgumentException("Null input");
        this.signalization = signalization;
    }

    @Override
    public void processEvent(SensorEvent event) {
        signalization.execute(event);
    }
}
