package ru.sbt.mipt.oop;

import java.util.Collection;
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
    public boolean execute(Function<Actionable, Boolean> action) {
        boolean resultItSelf = action.apply(this);
        Boolean resultLights;
        Boolean resultDoors;
        resultLights = lights.stream().map(s -> s.execute(action)).reduce((x, y) -> x || y).get();
        resultDoors = doors.stream().map(s -> s.execute(action)).reduce((x, y) -> x || y).get();
        return resultLights || resultDoors || resultItSelf;
    }

}
