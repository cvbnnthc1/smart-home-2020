package ru.sbt.mipt.oop;

public class DoorOpen implements Action {
    private final Door door;
    private final Room room;

    DoorOpen(Door door, Room room) {
        if (door == null || room == null) throw new IllegalArgumentException("Null input");
        this.door = door;
        this.room = room;
    }
    public void act() {
        door.setOpen(true);
        System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
    }
}
