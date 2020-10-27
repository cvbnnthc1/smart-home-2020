package ru.sbt.mipt.oop;


import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class SmartHome implements Actionable{
    private final Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean execute(Function<Actionable, Boolean> action) {
        boolean resultItSelf = action.apply(this);
        boolean resultRooms = rooms.stream().map(s -> s.execute(action)).reduce((x, y) -> x || y).get();
        return resultItSelf || resultRooms;
    }

}
