package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class LibraryEventManagerConfiguration {
    @Bean
    SensorEventsManager sensorEventsManager(Collection<EventHandlerAdapter> handlerAdapters) {
        SensorEventsManager result = new SensorEventsManager();
        for (EventHandlerAdapter handler: handlerAdapters) {
            result.registerEventHandler(handler);
        }
        return result;
    }

    @Bean
    Signalization signalization() {
        Signalization signalization = new Signalization();
        signalization.setState(new Deactivated(signalization));
        return signalization;
    }

    @Bean
    SmartHome smartHome() {
        return smartHomeReader().readSmartHome("smart-home-1.js");
    }

    @Bean
    SmartHomeReader smartHomeReader() {
        return new SmartHomeJSONReader();
    }

   @Bean
    Map<String, SensorEventType> eventTypeMap() {
        Map<String, SensorEventType> result = new HashMap<>();
        result.put("LightIsOn", SensorEventType.LIGHT_ON);
        result.put("LightIsOff", SensorEventType.LIGHT_OFF);
        result.put("DoorIsOpen", SensorEventType.DOOR_OPEN);
        result.put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
        result.put("DoorIsLocked", SensorEventType.ALARM_ACTIVATE);
        result.put("DoorIsUnlocked", SensorEventType.ALARM_DEACTIVATE);
        return result;
    }

    @Bean
    EventHandlerAdapter doorEventHandlerAdapter() {
        return new EventHandlerAdapter(new EventHandlerDecorator(new DoorEventHandler(smartHome(), commandSender()), signalization()),
                    eventTypeMap(),123);
    }

    @Bean
    EventHandlerAdapter lightEventHandlerAdapter() {
        return new EventHandlerAdapter(new EventHandlerDecorator(new LightEventHandler(smartHome(), commandSender()), signalization()),
                    eventTypeMap(),123);
    }

    @Bean
    EventHandlerAdapter hallDoorEventHandlerAdapter() {
        return new EventHandlerAdapter(new EventHandlerDecorator(new HallDoorEventHandler(smartHome(), commandSender()), signalization()),
                    eventTypeMap(),123);
    }

    @Bean
    EventHandlerAdapter signalizationEventHandlerAdapter() {
        return new EventHandlerAdapter(new EventHandlerDecorator(new SignalizationEventHandler(signalization(), commandSender()), signalization()),
                    eventTypeMap(),123);
    }

    @Bean
    CommandSender commandSender() {
        return new CommandSenderImpl();
    }

}
