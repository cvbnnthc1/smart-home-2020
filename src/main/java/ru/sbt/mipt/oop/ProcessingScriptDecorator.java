package ru.sbt.mipt.oop;

public class ProcessingScriptDecorator implements ProcessingScript {
    private final ProcessingScript processor;
    private final Signalization signalization;

    public ProcessingScriptDecorator(ProcessingScript standardProcessingScript, Signalization signalization) {
        if (signalization == null || standardProcessingScript == null) throw new IllegalArgumentException("Null input");
        this.processor = standardProcessingScript;
        this.signalization = signalization;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (signalization.getState() instanceof Alarm) {
            System.out.println("Sending sms " + event);
            if (event.getType() == SensorEventType.ALARM_CANCELING) {
                processor.processEvent(event);
            }
        } else {
            processor.processEvent(event);
        }
    }
}
