package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LightEventProcessorTest {
    static SmartHome smartHome;

    @Before
    public void readHome() {
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        this.smartHome = smartHome;
    }

    @Test
    public void processEvent_onFirstLight() {
        //given
        LightEventProcessor lightEventProcessor = new LightEventProcessor(smartHome, new SensorEvent(SensorEventType.LIGHT_ON, "1"));
        //when
        lightEventProcessor.processEvent();
        Light result = smartHome.getRooms().iterator().next().getLights().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertTrue(result.isOn());
    }

    @Test
    public void processEvent_offFirstLight() {
        //given
        LightEventProcessor lightEventProcessor = new LightEventProcessor(smartHome, new SensorEvent(SensorEventType.LIGHT_OFF, "1"));
        //when
        lightEventProcessor.processEvent();
        Light result = smartHome.getRooms().iterator().next().getLights().iterator().next();
        //then
        assertEquals("1", result.getId());
        assertFalse(result.isOn());
    }
}