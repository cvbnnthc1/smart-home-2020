package ru.sbt.mipt.oop;

public class EventHandlerDecorator implements EventHandler {
    private final EventHandler eventHandler;
    private final Signalization signalization;

    public EventHandlerDecorator(EventHandler eventHandler, Signalization signalization) {
        this.eventHandler = eventHandler;
        this.signalization = signalization;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (signalization.getState() instanceof Alarm) {
            System.out.println("Sending sms " + event);
            if (event.getType() == SensorEventType.ALARM_CANCELING) {
                eventHandler.processEvent(event);
            }
        } else {
            eventHandler.processEvent(event);
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> hometask-3
