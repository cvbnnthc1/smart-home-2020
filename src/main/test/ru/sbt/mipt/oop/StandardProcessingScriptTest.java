package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class StandardProcessingScriptTest {
    static SmartHome smartHome;

    @Before
    public void readHome() {
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        this.smartHome = smartHome;
    }

    @Test
    public void doScript_offAllLightsAfterCloseEntranceDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));
        //when
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, new MockSensor(events.iterator()));
        standardProcessingScript.doScript();
        //then
        for (Room room: smartHome.getRooms()) {
            for (Light light: room.getLights()) {
                assertFalse(light.isOn());
            }
        }
    }

    @Test
    public void doScript_closeFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_CLOSED, "1"));
        //when
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, new MockSensor(events.iterator()));
        standardProcessingScript.doScript();
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOpen());
    }

    @Test
    public void doScript_openFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //when
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, new MockSensor(events.iterator()));
        standardProcessingScript.doScript();
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOpen());
    }

    @Test
    public void doScript_offFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.LIGHT_OFF, "1"));
        //when
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, new MockSensor(events.iterator()));
        standardProcessingScript.doScript();
        Light result = smartHome.getRooms().iterator().next().getLights().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOn());
    }

    @Test
    public void doScript_onFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.LIGHT_ON, "1"));
        //when
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, new MockSensor(events.iterator()));
        standardProcessingScript.doScript();
        Light result = smartHome.getRooms().iterator().next().getLights().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOn());
    }

    class MockSensor implements Sensor {
        Iterator<SensorEvent> iterator;
        MockSensor(Iterator<SensorEvent> iterator) {
            this.iterator = iterator;
        }

        @Override
        public SensorEvent getNextSensorEvent() {
            if (iterator.hasNext()) {
                return iterator.next();
            } else {
                return null;
            }
        }
    }

}