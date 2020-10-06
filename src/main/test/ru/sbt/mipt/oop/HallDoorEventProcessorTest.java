package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HallDoorEventProcessorTest {
    SmartHome smartHome;

    @Before
    public void readHome() {
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        this.smartHome = smartHome;
    }

    @Test
    public void processEvent_offAllLights() {
        //given
        HallDoorEventProcessor hallDoorEventProcessor = new HallDoorEventProcessor(smartHome);
        //when
        hallDoorEventProcessor.processEvent();
        //then
        for (Room room: smartHome.getRooms()) {
            for (Light light: room.getLights()) {
                assertFalse(light.isOn());
            }
        }
    }

    @Test
    public void processEvent_offAllLightsAfterOnAllLights() {
        //given
        HallDoorEventProcessor hallDoorEventProcessor = new HallDoorEventProcessor(smartHome);
        //when
        for (int i = 1; i < 10; i++) {
            new LightEventProcessor(smartHome, new SensorEvent(SensorEventType.LIGHT_ON, "" + i)).processEvent();
        }
        hallDoorEventProcessor.processEvent();
        //then
        for (Room room: smartHome.getRooms()) {
            for (Light light: room.getLights()) {
                assertFalse(light.isOn());
            }
        }
    }
}