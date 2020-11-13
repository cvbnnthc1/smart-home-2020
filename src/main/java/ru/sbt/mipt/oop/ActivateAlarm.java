package ru.sbt.mipt.oop;

public class ActivateAlarm implements Command {
    private final EventHandler handler;

    public ActivateAlarm(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_ACTIVATE, null);
        handler.processEvent(event);
    }
}
