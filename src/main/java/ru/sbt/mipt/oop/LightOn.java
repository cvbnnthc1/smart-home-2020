package ru.sbt.mipt.oop;

public class LightOn implements Action {
    private final Light light;
    private final Room room;

    LightOn(Light light, Room room) {
        if (room == null || room == null) throw new IllegalArgumentException("Null input");
        this.light = light;
        this.room = room;
    }

    public void act() {
        light.setOn(true);
        System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
    }
}