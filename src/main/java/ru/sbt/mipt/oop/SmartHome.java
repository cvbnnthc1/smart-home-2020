package ru.sbt.mipt.oop;


import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable{
    private final Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    Collection<String> getEntranceDoorsIds() {
        for (Room room: rooms) {
            if (room.getName().equals("hall")) {
                return room.doorsIds();
            }
        }
        return null;
    }

    @Override
    public void execute(Action action) {
        rooms.stream().forEach(s -> s.execute(action));
    }

    Collection<Room> getRooms() {
        return rooms;
    }
}
