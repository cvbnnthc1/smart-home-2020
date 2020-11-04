package ru.sbt.mipt.oop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import rc.RemoteControlRegistry;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RemoteControlConfiguration {
    @Bean
    RemoteControlRegistry remoteControlRegistry() {
        RemoteControlRegistry remoteControlRegistry = new RemoteControlRegistry();
        remoteControlRegistry.registerRemoteControl(remoteControl(), remoteControl().getId());
        return remoteControlRegistry;
    }

    @Bean
    RemoteControlImpl remoteControl() {
        return new RemoteControlImpl(commandMap(), "1");
    }

    @Bean
    Map<String, Command> commandMap() {
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("A", activateAlarm());
        commandMap.put("B", alarmOn());
        commandMap.put("C", allLightsOff());
        commandMap.put("D", allLightsOn());
        commandMap.put("1", frontDoorClose());
        commandMap.put("2", hallLightsOn());
        commandMap.put("3", activateAlarm());
        commandMap.put("4", alarmOn());
        return commandMap;
    }

    @Bean
    Command activateAlarm() {
        return new ActivateAlarm(signalizationEventHandler());
    }

    @Bean
    Command alarmOn() {
        return new AlarmOn(signalizationEventHandler());
    }

    @Bean
    Command allLightsOff() {
        return new AllLightsOff(lightEventHandler());
    }

    @Bean
    Command allLightsOn() {
        return new AllLightsOn(lightEventHandler());
    }

    @Bean
    Command hallLightsOn() {
        return new HallLightsOn(lightEventHandler());
    }

    @Bean
    Command frontDoorClose() {
        return new FrontDoorClose(doorEventHandler());
    }

    @Bean
    EventHandler doorEventHandler() {
        return context().getBean("doorEventHandler", EventHandler.class);
    }

    @Bean
    EventHandler lightEventHandler() {
        return  context().getBean("lightEventHandler", EventHandler.class);
    }

    @Bean
    EventHandler signalizationEventHandler() {
        return context().getBean("signalizationEventHandler", EventHandler.class);
    }

    @Bean
    AbstractApplicationContext context() {
        return new AnnotationConfigApplicationContext(EventManagerConfiguration.class);
    }

    @Bean
    Signalization signalization() {
        return context().getBean(Signalization.class);
    }

    @Bean
    SmartHome smartHome() {
        return context().getBean(SmartHome.class);
    }
}
