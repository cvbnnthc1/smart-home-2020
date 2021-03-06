package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class StandardProcessingScriptTest {
    SmartHome smartHome;
    Map<String, List<Door>> doorsByRoom = new HashMap<>();
    Map<String, List<Light>> lightsByRoom = new HashMap<>();

    @Before
    public void readHome() {
        doorsByRoom = new HashMap<>();
        lightsByRoom = new HashMap<>();
        doorsByRoom.put("kitchen",  Arrays.asList(new Door(false, "1", "kitchen")));
        lightsByRoom.put("kitchen",  Arrays.asList(new Light("1", false), new Light("2", true)));
        Room kitchen = new Room(lightsByRoom.get("kitchen"),
                doorsByRoom.get("kitchen"),"kitchen");
        doorsByRoom.put("bathroom",  Arrays.asList(new Door(false, "2", "bathroom")));
        lightsByRoom.put("bathroom",  Arrays.asList(new Light("3", true)));
        Room bathroom = new Room(lightsByRoom.get("bathroom"),
                doorsByRoom.get("bathroom"), "bathroom");
        doorsByRoom.put("bedroom",  Arrays.asList(new Door(true, "3", "bedroom")));
        lightsByRoom.put("bedroom",  Arrays.asList(new Light("4", false), new Light("5", false), new Light("6", false)));
        Room bedroom = new Room(lightsByRoom.get("bedroom"), doorsByRoom.get("bedroom"), "bedroom");
        doorsByRoom.put("hall",  Arrays.asList(new Door(false, "4", "hall")));
        lightsByRoom.put("hall",  Arrays.asList(new Light("7", false), new Light("8", false), new Light("9", false)));
        Room hall = new Room(lightsByRoom.get("hall"),
                doorsByRoom.get("hall"), "hall");
        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, bathroom, bedroom, hall));
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
        for (String room: doorsByRoom.keySet()) {
            for (Light light: lightsByRoom.get(room)) {
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
        Door result = doorsByRoom.get("kitchen").get(0);
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
        Door result = doorsByRoom.get("kitchen").get(0);
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
        Light result = lightsByRoom.get("kitchen").get(0);
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
        Light result = lightsByRoom.get("kitchen").get(0);
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