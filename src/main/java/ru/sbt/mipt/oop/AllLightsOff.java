package ru.sbt.mipt.oop;

public class AllLightsOff implements Command {
    private final EventHandler handler;

    public AllLightsOff(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        SensorEvent event = new SensorEvent(SensorEventType.ALL_LIGHTS_OFF, null);
        handler.processEvent(event);
    }
}
