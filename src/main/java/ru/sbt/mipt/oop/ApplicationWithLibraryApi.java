package ru.sbt.mipt.oop;

import java.util.HashMap;
import java.util.Map;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ApplicationWithLibraryApi {
    public static void main(String[] args) {
        // считываем состояние дома из файла
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(EventManagerConfiguration.class);
        EventHandler[] handlers = context.getBean(EventHandler[].class);
        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        //{ "LightIsOn", "LightIsOff", "DoorIsOpen", "DoorIsClosed", "DoorIsLocked", "DoorIsUnlocked" };
        Map<String, SensorEventType> eventTypeMap = new HashMap<>();
        eventTypeMap.put("LightIsOn", SensorEventType.LIGHT_ON);
        eventTypeMap.put("LightIsOff", SensorEventType.LIGHT_OFF);
        eventTypeMap.put("DoorIsOpen", SensorEventType.DOOR_OPEN);
        eventTypeMap.put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
        eventTypeMap.put("DoorIsLocked", SensorEventType.ALARM_ACTIVATE);
        eventTypeMap.put("DoorIsUnlocked", SensorEventType.ALARM_DEACTIVATE);
        for (EventHandler handler: handlers) {
            sensorEventsManager.registerEventHandler(new EventProcessorAdapter(handler, eventTypeMap, 123));
        }
        sensorEventsManager.start();


    }
}
