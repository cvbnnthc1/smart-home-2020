package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StandardProcessingScriptTest {
    static SmartHome smartHome;
    static List<EventProcessor> processors;
    @Before
    public void readHome() {
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        this.smartHome = smartHome;
        this.processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
    }

    @Test
    public void processEvent_offAllLightsAfterCloseEntranceDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));
        //when
        Signalization signalization = new Signalization();
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, signalization, processors);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        //then
        for (Room room: smartHome.getRooms()) {
            for (Light light: room.getLights()) {
                assertFalse(light.isOn());
            }
        }
    }

    @Test
    public void processEvent_closeFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_CLOSED, "1"));
        //when
        Signalization signalization = new Signalization();
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, signalization, processors);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOpen());
    }

    @Test
    public void processEvent_openFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //when
        Signalization signalization = new Signalization();
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, signalization, processors);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOpen());
    }

    @Test
    public void processEvent_offFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.LIGHT_OFF, "1"));
        //when
        Signalization signalization = new Signalization();
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, signalization, processors);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        Light result = smartHome.getRooms().iterator().next().getLights().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOn());
    }

    @Test
    public void processEvent_onFirstDoor() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.LIGHT_ON, "1"));
        //when
        Signalization signalization = new Signalization();
        StandardProcessingScript standardProcessingScript = new StandardProcessingScript(smartHome, signalization, processors);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        Light result = smartHome.getRooms().iterator().next().getLights().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOn());
    }


}