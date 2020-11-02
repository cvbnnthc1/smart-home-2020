package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ApplicationWithLibraryApi {
    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(LibraryEventManagerConfiguration.class);
        SensorEventsManager eventManager = context.getBean(SensorEventsManager.class);
        eventManager.start();
        System.out.println("");
    }
}
