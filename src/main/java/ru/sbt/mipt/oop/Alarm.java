package ru.sbt.mipt.oop;

public class Alarm implements State {
    private final Signalization signalization;

    Alarm(Signalization signalization) {
        this.signalization = signalization;
    }

    @Override
    public void execute(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_CANCELING) {
            signalization.setState(new Deactivated(signalization));
        }
    }
}
