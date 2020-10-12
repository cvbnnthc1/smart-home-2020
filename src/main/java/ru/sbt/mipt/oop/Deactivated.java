package ru.sbt.mipt.oop;

public class Deactivated implements State {
    private final Signalization signalization;

    Deactivated(Signalization signalization) {
        this.signalization = signalization;
    }

    @Override
    public void execute(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_ACTIVATE) {
            signalization.setState(new Activated(signalization, event.getCode()));
        } else if(event.getType() == SensorEventType.ALARM_DEACTIVATE) {
            System.out.println("Already deactivated");
            signalization.setState(new Alarm(signalization));
        } else if(event.getType() == SensorEventType.ALARM_CANCELING) {
            System.out.println("Signalization is not in Alarm state, queerly...");
            signalization.setState(new Alarm(signalization));
        }
    }
}
