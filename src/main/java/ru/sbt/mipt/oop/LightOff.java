package ru.sbt.mipt.oop;

public class LightOff implements Action {
    private final Light light;
    private final Room room;

    LightOff(Light light, Room room) {
        if (light == null || room == null) throw new IllegalArgumentException("Null input");
        this.light = light;
        this.room = room;
    }

    public void act() {
        light.setOn(false);
        System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
    }

}
