package ru.sbt.mipt.oop;

public class SignalizationEventHandler implements EventHandler {
    public final Signalization signalization;
    public final CommandSender commandSender;

    SignalizationEventHandler(Signalization signalization, CommandSender commandSender) {
        this.commandSender = commandSender;
        if (signalization == null) throw new IllegalArgumentException("Null input");
        this.signalization = signalization;
    }

    @Override
    public void processEvent(SensorEvent event) {
        signalization.execute(event);
        if (event.getType() == SensorEventType.ALARM_ACTIVATE) {
            SensorCommand command = new SensorCommand(CommandType.ACTIVATE_SIGNALIZATION, "signalization");
            commandSender.sendCommand(command);
        } else if (event.getType() == SensorEventType.ALARM_ON) {
            SensorCommand command = new SensorCommand(CommandType.ALARM_SIGNALIZATION, "signalization");
            commandSender.sendCommand(command);
        }
    }
}
