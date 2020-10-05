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
        ProcessingScript processor = new StandardProcessingScript();
        processor.doScript(smartHome, sensor);
    }


}
