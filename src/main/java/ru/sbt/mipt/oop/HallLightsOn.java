package ru.sbt.mipt.oop;

public class HallLightsOn implements Command {
    private final EventHandler handler;

    public HallLightsOn(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_ON_IN_HALL, null);
        handler.processEvent(event);
    }
}
