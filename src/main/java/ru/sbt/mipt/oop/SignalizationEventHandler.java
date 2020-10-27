package ru.sbt.mipt.oop;

public class SignalizationEventHandler implements EventHandler {
    public final Signalization signalization;

    SignalizationEventHandler(Signalization signalization) {
        if (signalization == null) throw new IllegalArgumentException("Null input");
        this.signalization = signalization;
    }

    @Override
    public void processEvent(SensorEvent event) {
        signalization.execute(event);
    }
}
