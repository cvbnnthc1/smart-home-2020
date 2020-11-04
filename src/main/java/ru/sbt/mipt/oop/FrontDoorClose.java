package ru.sbt.mipt.oop;

public class FrontDoorClose implements Command {
    private final EventHandler handler;

    public FrontDoorClose(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        SensorEvent event = new SensorEvent(SensorEventType.CLOSE_FRONT_DOOR, null);
        handler.processEvent(event);
    }
}
