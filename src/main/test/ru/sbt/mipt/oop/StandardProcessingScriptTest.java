package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        List<EventProcessor> processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
        ProcessingScript processor = new StandardProcessingScript(processors);
        processor.executeScript(smartHome, new MockSensorEventProvider(events.iterator()));
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
        List<EventProcessor> processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
        ProcessingScript processor = new StandardProcessingScript(processors);
        processor.executeScript(smartHome, new MockSensorEventProvider(events.iterator()));
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
        List<EventProcessor> processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
        ProcessingScript processor = new StandardProcessingScript(processors);
        processor.executeScript(smartHome, new MockSensorEventProvider(events.iterator()));
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
        List<EventProcessor> processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
        ProcessingScript processor = new StandardProcessingScript(processors);
        processor.executeScript(smartHome, new MockSensorEventProvider(events.iterator()));
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
        List<EventProcessor> processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
        ProcessingScript processor = new StandardProcessingScript(processors);
        processor.executeScript(smartHome, new MockSensorEventProvider(events.iterator()));
        Light result = smartHome.getRooms().iterator().next().getLights().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOn());
    }

    class MockSensorEventProvider implements SensorEventProvider {
        Iterator<SensorEvent> iterator;
        MockSensorEventProvider(Iterator<SensorEvent> iterator) {
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