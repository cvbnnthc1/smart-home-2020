package ru.sbt.mipt.oop;


import java.util.ArrayList;
import java.util.Collection;

public class SmartHome {
    Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) return room;
        }
        return null;
    }

    public Room getRoomByDoor(String id) {
        for (Room room : rooms) {
            Door curDoor = room.getDoor(id);
            if (curDoor != null) {
                return room;
            }
        }
        return null;
    }

    public Room getRoomByLight(String id) {
        for (Room room : rooms) {
            Light curLight = room.getLight(id);
            if (curLight != null) {
                return room;
            }
        }
        return null;
    }
}
