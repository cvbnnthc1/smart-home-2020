package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoorEventProcessorTest {
    SmartHome smartHome;

    @Before
    public void readHome() {
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        this.smartHome = smartHome;
    }

    @Test
    public void processEvent_openFirstDoor() {
        //given
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome, new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //when
        doorEventProcessor.processEvent();
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOpen());
    }

    @Test
    public void processEvent_closeFirstDoor() {
        //given
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome, new SensorEvent(SensorEventType.DOOR_CLOSED, "1"));
        //when
        doorEventProcessor.processEvent();
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOpen());
    }
}