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
        List<EventProcessor> processors = new ArrayList<>();
        processors.add(new DoorEventProcessor(smartHome));
        processors.add(new LightEventProcessor(smartHome));
        processors.add(new HallDoorEventProcessor(smartHome));
        processors.add(new SignalizationEventProcessor(signalization));
        ProcessingScript processor = new StandardProcessingScript(processors);
        SensorEvent event = sensor.getNextSensorEvent();
        while (event != null) {
            processor.processEvent(event);
            event = sensor.getNextSensorEvent();
        }
    }

}
