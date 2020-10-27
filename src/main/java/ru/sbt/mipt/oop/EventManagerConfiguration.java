package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
public class EventManagerConfiguration {

    @Bean
    EventManager eventManager() {
        return new StandardEventManager(Arrays.asList(eventHandlers()), sensorEventProvider());
    }

    @Bean
    Signalization signalization() {
        Signalization signalization = new Signalization();
        signalization.setState(new Deactivated(signalization));
        return signalization;
    }

    @Bean
    SmartHome smartHome() {
        return smartHomeReader().readSmartHome(source());
    }

    @Bean
    SmartHomeReader smartHomeReader() {
        return new SmartHomeJSONReader();
    }

    @Bean
    String source() {
        return "smart-home-1.js";
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
        return new EventHandlerDecorator(new HallDoorEventHandler(smartHome(), commandSender()), signalization());
    }

    @Bean
    EventHandler signalizationEventHandler() {
        return new EventHandlerDecorator(new SignalizationEventHandler(signalization()), signalization());
    }

    @Bean SensorEventProvider sensorEventProvider() {
        return new RandomSensorEventProvider();
    }

    @Bean
    EventHandler[] eventHandlers() {
        return new EventHandler[]{doorEventHandler(),
                lightEventHandler(),
                hallDoorEventHandler(),
                signalizationEventHandler()};
    }

    @Bean
    CommandSender commandSender() {
        return new CommandSenderImpl();
    }

}
