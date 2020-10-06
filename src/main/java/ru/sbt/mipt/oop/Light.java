package ru.sbt.mipt.oop;

public class Light implements HomeComponent{
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
}
