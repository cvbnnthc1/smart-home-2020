package ru.sbt.mipt.oop;

public class Deactivated extends SignalizationState {
    Deactivated(Signalization signalization) {
        super(signalization);
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_ACTIVATE) {
            activate(event.getCode());
        } else if(event.getType() == SensorEventType.ALARM_DEACTIVATE) {
            System.out.println("Already deactivated");
            alarm();
        } else if(event.getType() == SensorEventType.ALARM_CANCELING) {
            System.out.println("Signalization is not in Alarm state, queerly...");
            alarm();
        } else if (event.getType() == SensorEventType.ALARM_ON) {
            System.out.println("Event alarm on");
            alarm();
        }
    }
}
