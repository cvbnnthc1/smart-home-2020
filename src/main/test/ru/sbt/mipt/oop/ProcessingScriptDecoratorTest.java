package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProcessingScriptDecoratorTest {
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
    public void processEvent_offAllLightsAfterCloseEntranceDoor_ifSignalizationInNormalState() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.DOOR_CLOSED, "4"));
        //when
        Signalization signalization = new Signalization();
        signalization.setState(new Activated(signalization,3));
        processors.add(new SignalizationEventProcessor(signalization));
        ProcessingScript standardProcessingScript = new ProcessingScriptDecorator(new StandardProcessingScript(smartHome, signalization, processors), signalization);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        //then
        for (Room room: smartHome.getRooms()) {
            for (Light light: room.getLights()) {
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
        Signalization signalization = new Signalization();
        signalization.setState(new Activated(signalization,123));
        processors.add(new SignalizationEventProcessor(signalization));
        ProcessingScript standardProcessingScript = new ProcessingScriptDecorator(new StandardProcessingScript(smartHome, signalization, processors), signalization);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        //then
        boolean result = false;
        for (Room room: smartHome.getRooms()) {
            for (Light light: room.getLights()) {
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
        Signalization signalization = new Signalization();
        signalization.setState(new Activated(signalization,123));
        processors.add(new SignalizationEventProcessor(signalization));
        ProcessingScript standardProcessingScript = new ProcessingScriptDecorator(new StandardProcessingScript(smartHome, signalization, processors), signalization);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
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
        Signalization signalization = new Signalization();
        signalization.setState(new Alarm(signalization));
        processors.add(new SignalizationEventProcessor(signalization));
        ProcessingScript standardProcessingScript = new ProcessingScriptDecorator(new StandardProcessingScript(smartHome, signalization, processors), signalization);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertFalse(result.isOpen());
    }

    @Test
    public void processEvent_openFirstDoor_whenAlarmCancelling() {
        //given
        ArrayList<SensorEvent> events = new ArrayList<>();
        events.add(new SensorEvent(SensorEventType.ALARM_CANCELING, "1", 123));
        events.add(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //when
        Signalization signalization = new Signalization();
        signalization.setState(new Alarm(signalization));
        processors.add(new SignalizationEventProcessor(signalization));
        ProcessingScript standardProcessingScript = new ProcessingScriptDecorator(new StandardProcessingScript(smartHome, signalization, processors), signalization);
        for (SensorEvent event: events) standardProcessingScript.processEvent(event);
        Door result = smartHome.getRooms().iterator().next().getDoors().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOpen());
    }
}