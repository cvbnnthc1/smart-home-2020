package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collection;

@Configuration
public class EventManagerConfiguration {

    @Bean
    EventManager eventManager(Collection<EventHandler> handlers) {
        return new StandardEventManager(handlers, new RandomSensorEventProvider());
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


}
