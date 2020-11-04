package ru.sbt.mipt.oop;

public class AlarmOn implements Command {
    private final EventHandler handler;

    public AlarmOn(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_ON, null);
        handler.processEvent(event);
    }
}
