package ru.sbt.mipt.oop;

import java.util.Collection;
import java.util.HashSet;

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

    Collection<String> doorsIds() {
        HashSet<String> result = new HashSet<>();
        for (Door door: doors) {
            result.add(door.getId());
        }
        return result;
    }

    @Override
    public void execute(Action action) {
        lights.stream().forEach(s -> {
            action.execute(s);
        });
        doors.stream().forEach(s -> {
            action.execute(s);
        });
    }

    Collection<Door> getDoors() {
        return doors;
    }

    Collection<Light> getLights() {
        return lights;
    }
}
