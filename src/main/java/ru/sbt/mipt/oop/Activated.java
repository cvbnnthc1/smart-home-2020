package ru.sbt.mipt.oop;

public class Activated extends SignalizationState {
    private final int code;

    Activated(Signalization signalization, int code) {
        super(signalization);
        this.code = code;
    }

    private boolean checkCode(int code) {
        return this.code == code;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_DEACTIVATE) {
            if (checkCode(event.getCode())) {
                deactivate();
            } else {
                System.out.println("Wrong deactivation code");
                alarm();
            }
        } else if(event.getType() == SensorEventType.ALARM_ACTIVATE) {
            System.out.println("Already activated");
            alarm();
        } else if(event.getType() == SensorEventType.ALARM_CANCELING) {
            System.out.println("Signalization is not in Alarm state, queerly...");
            alarm();
        } else if (event.getType() == SensorEventType.ALARM_ON) {
            System.out.println("Event alarm on");
            alarm();
        }
        else {
            System.out.println("Event when signalization activated");
            alarm();
        }
    }
}
