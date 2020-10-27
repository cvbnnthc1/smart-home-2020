package ru.sbt.mipt.oop;

public class CommandSenderImpl implements CommandSender {
    @Override
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretent we're sending command " + command);
    }
}
