package ru.sbt.mipt.oop;

import java.io.IOException;

public class Application {

    public static void main(String... args) throws IOException {
        // считываем состояние дома из файла
        SmartHomeReader reader = new SmartHomeJSONReader();
        String source = "smart-home-1.js";
        SmartHome smartHome = reader.readSmartHome(source);
        // начинаем цикл обработки событий
        Sensor sensor = new RandomSensor();
        SensorEvent event = sensor.getNextSensorEvent();
        EventProcessor processor = new StandardEventProcessor();
        while (event != null) {
            processor.processEvent(smartHome, event);
            event = sensor.getNextSensorEvent();
        }
    }


}
