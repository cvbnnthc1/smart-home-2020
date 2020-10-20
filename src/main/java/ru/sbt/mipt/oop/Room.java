package ru.sbt.mipt.oop;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

public class Room implements Actionable{
    private final Collection<Light> lights;
    private final Collection<Door> doors;
    private final String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean execute(Function<HomeComponent, Boolean> action) {
        Boolean resultLights;
        Boolean resultDoors;
        resultLights = lights.stream().map(s -> action.apply(s)).reduce((x, y) -> x || y).get();
        resultDoors = doors.stream().map(s -> action.apply(s)).reduce((x, y) -> x || y).get();
        return resultLights || resultDoors;
    }

    //Методы для юнит тестов
    Collection<Light> getLights() {
        return lights;
    }

    Collection<Door> getDoors() {
        return doors;
    }

}
