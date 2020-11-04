package ru.sbt.mipt.oop;

import rc.RemoteControl;

import java.util.Map;

public class RemoteControlImpl implements RemoteControl {
    private final Map<String, Command> commandMap;
    private final String id;

    public RemoteControlImpl(Map<String, Command> commandMap, String id) {
        this.commandMap = commandMap;
        this.id = id;
    }

    @Override
    public void onButtonPressed(String buttonCode) {
        Command command = commandMap.get(buttonCode);
        if (command != null) {
            command.execute();
        }
    }

    String getId() {
        return id;
    }
}
