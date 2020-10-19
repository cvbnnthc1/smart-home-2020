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
    public boolean execute(Function<HomeComponent, Boolean> action) {
        return rooms.stream().map(s -> s.execute(action)).reduce((x, y) -> x || y).get();
    }

    //Метод для  юнит-тестов
    Collection<Room> getRooms() {
        return rooms;
    }
}
