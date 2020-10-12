package ru.sbt.mipt.oop;

public class Activated implements State {
    private final Signalization signalization;
    private final int code;

    Activated(Signalization signalization, int code) {
        this.signalization = signalization;
        this.code = code;
    }

    @Override
    public void execute(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_DEACTIVATE) {
            if (event.getCode() == code) {
                signalization.setState(new Deactivated(signalization));
            } else {
                System.out.println("Wrong deactivation code");
                signalization.setState(new Alarm(signalization));
            }
        } else if(event.getType() == SensorEventType.ALARM_ACTIVATE) {
            System.out.println("Already activated");
            signalization.setState(new Alarm(signalization));
        } else if(event.getType() == SensorEventType.ALARM_CANCELING) {
            System.out.println("Signalization is not in Alarm state, queerly...");
            signalization.setState(new Alarm(signalization));
        }
    }
}
