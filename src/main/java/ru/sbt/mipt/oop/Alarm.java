package ru.sbt.mipt.oop;

public class Alarm extends SignalizationState {
    Alarm(Signalization signalization) {
        super(signalization);
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_CANCELING) {
            deactivate();
        }
    }

}
