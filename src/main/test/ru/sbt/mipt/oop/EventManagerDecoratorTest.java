package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class EventManagerDecoratorTest {
    SmartHome smartHome;
    Map<String, List<Door>> doorsByRoom = new HashMap<>();
    Map<String, List<Light>> lightsByRoom = new HashMap<>();
    List<EventHandler> processors = new ArrayList<>();
    Signalization signalization;

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
        signalization = new Signalization();
        CommandSender commandSender = new CommandSenderImpl();
        processors.add(new EventHandlerDecorator(new DoorEventHandler(smartHome), signalization));
        processors.add(new EventHandlerDecorator(new LightEventHandler(smartHome), signalization));
        processors.add(new EventHandlerDecorator(new HallDoorEventHandler(smartHome, commandSender), signalization));
        processors.add(new EventHandlerDecorator(new SignalizationEventHandler(signalization), signalization));
    }

    @Test
    public void processEvent_offAllLightsAfterCloseEntranceDoor_ifSignalizationInNormalState() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));
        //when
        signalization.setState(new Activated(signalization,3));
        processors.add(new SignalizationEventHandler(signalization));
        MockSensorEventProvider sensor = new MockSensorEventProvider(events.iterator());
        StandardEventManager standardEventManager = new StandardEventManager(processors, sensor);
        standardEventManager.start();
        //then
        for (String room: lightsByRoom.keySet()) {
            for (Light light: lightsByRoom.get(room)) {
                assertFalse(light.isOn());
            }
        }
    }

    @Test
    public void processEvent_notOffAllLightsAfterCloseEntranceDoor_ifSignalizationInAlarmState() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.ALARM_DEACTIVATE, "123", 13));
        events.add(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));
        //when
        signalization.setState(new Activated(signalization,123));
        MockSensorEventProvider sensor = new MockSensorEventProvider(events.iterator());
        StandardEventManager standardEventManager = new StandardEventManager(processors, sensor);
        standardEventManager.start();
        //then
        boolean result = false;
        for (String room: lightsByRoom.keySet()) {
            for (Light light: lightsByRoom.get(room)) {
                result |= light.isOn();
            }
        }
        assertTrue(result);
    }

    @Test
    public void processEvent_openFirstDoor_whenSuccessfulDeactivated() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.ALARM_DEACTIVATE, "1", 123));
        events.add(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //when
        signalization.setState(new Activated(signalization,123));
        MockSensorEventProvider sensor = new MockSensorEventProvider(events.iterator());
        StandardEventManager standardEventManager = new StandardEventManager(processors, sensor);
        standardEventManager.start();
        Door result = doorsByRoom.get("kitchen").get(0);
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOpen());
    }

    @Test
    public void processEvent_notOpenFirstDoor_whenAlarmState() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //when
        signalization.setState(new Alarm(signalization));
        MockSensorEventProvider sensor = new MockSensorEventProvider(events.iterator());
        StandardEventManager standardEventManager = new StandardEventManager(processors, sensor);
        standardEventManager.start();
        Door result = doorsByRoom.get("kitchen").get(0);
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOpen());
    }

    @Test
    public void processEvent_openFirstDoor_whenAlarmCancelling() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.ALARM_CANCELING, "1", 123));
        events.add(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //when
        signalization.setState(new Alarm(signalization));
        MockSensorEventProvider sensor = new MockSensorEventProvider(events.iterator());
        StandardEventManager standardEventManager = new StandardEventManager(processors, sensor);
        standardEventManager.start();
        Door result = doorsByRoom.get("kitchen").get(0);
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOpen());
    }

    private static class MockSensorEventProvider implements SensorEventProvider {
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