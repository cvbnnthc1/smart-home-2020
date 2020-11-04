package ru.sbt.mipt.oop;

import java.io.IOException;
import java.util.Arrays;

public class HomeBuilder {

    public static void main(String[] args) throws IOException {
        SmartHome smartHome = buildSmartHome();
        SmartHomeWriter writer = new SmartHomeJSONWriter();
        String target = "smart-home-1.js";
        writer.writeSmartHome(smartHome, target);
    }

    public static SmartHome buildSmartHome() {
        Room kitchen = new Room(Arrays.asList(new Light("1", false, "kitchen"), new Light("2", true, "kitchen")),
                Arrays.asList(new Door(false, "1", "kitchen")),
                "kitchen");
        Room bathroom = new Room(Arrays.asList(new Light("3", true, "bathroom")),
                Arrays.asList(new Door(false, "2", "bathroom")),
                "bathroom");
        Room bedroom = new Room(Arrays.asList(new Light("4", false, "bedroom"), new Light("5", false, "bedroom"), new Light("6", false, "bedroom")),
                Arrays.asList(new Door(true, "3", "bedroom")),
                "bedroom");
        Room hall = new Room(Arrays.asList(new Light("7", false, "hall"), new Light("8", false, "hall"), new Light("9", false, "hall")),
                Arrays.asList(new Door(false, "4", "hall")),
                "hall");
        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, bathroom, bedroom, hall));
        return smartHome;
    }

}
