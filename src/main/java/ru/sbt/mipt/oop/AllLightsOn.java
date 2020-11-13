package ru.sbt.mipt.oop;

public class AllLightsOn implements Command {
    private final EventHandler handler;

    public AllLightsOn(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        SensorEvent event = new SensorEvent(SensorEventType.ALL_LIGHTS_ON, null);
        handler.processEvent(event);
    }
}
