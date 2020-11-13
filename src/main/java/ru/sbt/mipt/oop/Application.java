package ru.sbt.mipt.oop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;

public class Application {

    public static void main(String... args) throws IOException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(EventManagerConfiguration.class);
        StandardEventManager eventManager = context.getBean(StandardEventManager.class);
        eventManager.start();
    }

}
