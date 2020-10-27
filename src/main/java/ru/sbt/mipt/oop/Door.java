package ru.sbt.mipt.oop;

import java.util.function.Function;

public class Door implements HomeComponent, Actionable {
    private final String id;
    private boolean isOpen;
    private final String roomName;

    public Door(boolean isOpen, String id, String roomName) {
        this.isOpen = isOpen;
        this.id = id;
        this.roomName = roomName;
    }

    boolean isOpen() {
        return isOpen;
    }

    public String getId() {
        return id;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    String getRoomName() {
        return roomName;
    }

    @Override
    public boolean execute(Function<Actionable, Boolean> action) {
        return action.apply(this);
    }
}
