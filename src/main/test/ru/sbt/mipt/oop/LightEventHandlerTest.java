package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LightEventHandlerTest {
    SmartHome smartHome;
    Map<String, List<Door>> doorsByRoom = new HashMap<>();
    Map<String, List<Light>> lightsByRoom = new HashMap<>();
    CommandSender commandSender = new CommandSenderImpl();

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
        this.smartHome = smartHome;
    }

    @Test
    public void processEvent_onFirstLight() {
        //given
        LightEventHandler lightEventProcessor = new LightEventHandler(smartHome, commandSender);
        //when
        lightEventProcessor.processEvent(new SensorEvent(SensorEventType.LIGHT_ON, "1"));
        Light result = lightsByRoom.get("kitchen").get(0);
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOn());
    }

    @Test
    public void processEvent_offFirstLight() {
        //given
        LightEventHandler lightEventProcessor = new LightEventHandler(smartHome, commandSender);
        //when
        lightEventProcessor.processEvent(new SensorEvent(SensorEventType.LIGHT_OFF, "1"));
        Light result = lightsByRoom.get("kitchen").get(0);
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOn());
    }


}