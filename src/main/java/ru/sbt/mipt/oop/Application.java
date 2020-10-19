package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        // начинаем цикл обработки событий
        SensorEventProvider sensorEventProvider = new RandomSensorEventProvider();
        List<EventProcessor> processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
        ProcessingScript processor = new StandardProcessingScript(processors);
        processor.executeScript(smartHome, sensorEventProvider);
    }


}
