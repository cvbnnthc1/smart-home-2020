package ru.sbt.mipt.oop;

public class Signalization {
    private SignalizationState signalizationState;

    public void execute(SensorEvent event) {
        if (signalizationState instanceof Activated) {
            handleEventWhenActivated(event);
        } else if (signalizationState instanceof Deactivated) {
            handleEventWhenDeactivated(event);
        } else if (signalizationState instanceof Alarm) {
            handleEventWhenAlarm(event);
        }
    }

    public SignalizationState getState() {
        return signalizationState;
    }

    void setState(SignalizationState signalizationState) {
        this.signalizationState = signalizationState;
    }

    void handleEventWhenActivated(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_DEACTIVATE) {
            if (((Activated) signalizationState).checkCode(event.getCode())) {
                signalizationState.deactivate();
            } else {
                System.out.println("Wrong deactivation code");
                signalizationState.alarm();
            }
        } else if(event.getType() == SensorEventType.ALARM_ACTIVATE) {
            System.out.println("Already activated");
            signalizationState.alarm();
        } else if(event.getType() == SensorEventType.ALARM_CANCELING) {
            System.out.println("Signalization is not in Alarm state, queerly...");
            signalizationState.alarm();
        }
    }

    void handleEventWhenDeactivated(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_ACTIVATE) {
            signalizationState.activate(event.getCode());
        } else if(event.getType() == SensorEventType.ALARM_DEACTIVATE) {
            System.out.println("Already deactivated");
            signalizationState.alarm();
        } else if(event.getType() == SensorEventType.ALARM_CANCELING) {
            System.out.println("Signalization is not in Alarm state, queerly...");
            signalizationState.alarm();
        }
    }

    void handleEventWhenAlarm(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_CANCELING) {
            signalizationState.deactivate();
        }
    }
}
