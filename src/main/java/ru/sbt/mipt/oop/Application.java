package ru.sbt.mipt.oop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String... args) throws IOException {
        // считываем состояние дома из файла
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        // начинаем цикл обработки событий
        SensorEventProvider sensor = new RandomSensorEventProvider();
        Signalization signalization = new Signalization();
        signalization.setState(new Deactivated(signalization));
        List<EventHandler> processors = new ArrayList<>();
        CommandSender commandSender = new CommandSenderImpl();
        processors.add(new EventHandlerDecorator(new DoorEventHandler(smartHome), signalization));
        processors.add(new EventHandlerDecorator(new LightEventHandler(smartHome), signalization));
        processors.add(new EventHandlerDecorator(new HallDoorEventHandler(smartHome, commandSender), signalization));
        processors.add(new EventHandlerDecorator(new SignalizationEventHandler(signalization), signalization));
        processors.add(new SignalizationEventHandler(signalization));
        signalization.setState(new Deactivated(signalization));
        EventManager processor = new StandardEventManager(processors, sensor);
        processor.start();
    }

}
