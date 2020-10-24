package ru.sbt.mipt.oop;

import java.util.function.Function;

public class Light implements HomeComponent, Actionable{
    private boolean isOn;
    private final String id;

    public Light(String id, boolean isOn) {
        this.id = id;
        this.isOn = isOn;
    }

    boolean isOn() {
        return isOn;
    }

    public String getId() {
        return id;
    }

    void setOn(boolean on) {
        isOn = on;
    }

    @Override
    public boolean execute(Function<Actionable, Boolean> action) {
        return action.apply(this);
    }
}
