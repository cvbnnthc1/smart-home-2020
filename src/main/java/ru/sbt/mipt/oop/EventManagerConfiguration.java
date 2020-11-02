package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EventManagerConfiguration {

    @Bean
    EventManager eventManager() {
        return new StandardEventManager(handlerList(), new RandomSensorEventProvider());
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
    EventHandler doorEventHandler() {
        return new EventHandlerDecorator(new DoorEventHandler(smartHome()), signalization());
    }

    @Bean
    EventHandler lightEventHandler() {
        return new EventHandlerDecorator(new LightEventHandler(smartHome()), signalization());
    }

    @Bean
    EventHandler hallDoorEventHandler() {
        return new EventHandlerDecorator(new HallDoorEventHandler(smartHome(), new CommandSenderImpl()), signalization());
    }

    @Bean
    EventHandler signalizationEventHandler() {
        return new EventHandlerDecorator(new SignalizationEventHandler(signalization()), signalization());
    }

    @Bean
    List<EventHandler> handlerList() {
        List<EventHandler> handlerList = new ArrayList<>();
        handlerList.add(doorEventHandler());
        handlerList.add(lightEventHandler());
        handlerList.add(hallDoorEventHandler());
        handlerList.add(signalizationEventHandler());
        return handlerList;
    };

}
