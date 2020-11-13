package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class HallDoorEventHandlerTest {
    SmartHome smartHome;
    Map<String, List<Door>> doorsByRoom = new HashMap<>();
    Map<String, List<Light>> lightsByRoom = new HashMap<>();
    CommandSender commandSender;

    @Before
    public void readHome() {
        doorsByRoom = new HashMap<>();
        lightsByRoom = new HashMap<>();
        doorsByRoom.put("kitchen",  Arrays.asList(new Door(false, "1", "kitchen")));
        lightsByRoom.put("kitchen",  Arrays.asList(new Light("1", false, "kitchen"), new Light("2", true, "kitchen")));
        Room kitchen = new Room(lightsByRoom.get("kitchen"),
                doorsByRoom.get("kitchen"),"kitchen");
        doorsByRoom.put("bathroom",  Arrays.asList(new Door(false, "2", "bathroom")));
        lightsByRoom.put("bathroom",  Arrays.asList(new Light("3", true, "bathroom")));
        Room bathroom = new Room(lightsByRoom.get("bathroom"),
                doorsByRoom.get("bathroom"), "bathroom");
        doorsByRoom.put("bedroom",  Arrays.asList(new Door(true, "3", "bedroom")));
        lightsByRoom.put("bedroom",  Arrays.asList(new Light("4", false, "bedroom"), new Light("5", false, "bedroom"), new Light("6", false, "bedroom")));
        Room bedroom = new Room(lightsByRoom.get("bedroom"), doorsByRoom.get("bedroom"), "bedroom");
        doorsByRoom.put("hall",  Arrays.asList(new Door(false, "4", "hall")));
        lightsByRoom.put("hall",  Arrays.asList(new Light("7", false, "hall"), new Light("8", false, "hall"), new Light("9", false, "hall")));
        Room hall = new Room(lightsByRoom.get("hall"),
                doorsByRoom.get("hall"), "hall");
        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, bathroom, bedroom, hall));
        commandSender = new CommandSenderImpl();
        this.smartHome = smartHome;
    }

    @Test
    public void processEvent_offAllLights() {
        //given
        HallDoorEventHandler hallDoorEventProcessor = new HallDoorEventHandler(smartHome, commandSender);
        //when
        hallDoorEventProcessor.processEvent(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));
        //then
        for (String room: lightsByRoom.keySet()) {
            for (Light light: lightsByRoom.get(room)) {
                assertFalse(light.isOn());
            }
        }
    }

    @Test
    public void processEvent_offAllLightsAfterOnAllLights() {
        //given
        HallDoorEventHandler hallDoorEventProcessor = new HallDoorEventHandler(smartHome, commandSender);
        //when
        for (int i = 1; i < 10; i++) {
            new LightEventHandler(smartHome, commandSender).processEvent(new SensorEvent(SensorEventType.LIGHT_ON, "" + i));
        }
        hallDoorEventProcessor.processEvent(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));
        //then
        for (String room: lightsByRoom.keySet()) {
            for (Light light: lightsByRoom.get(room)) {
                assertFalse(light.isOn());
            }
        }
    }


}