package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartHomeJSONReader implements SmartHomeReader{

    public  SmartHome readSmartHome(String source) {
        Gson gson = new Gson();
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(source)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't open file", e);
        }
        return gson.fromJson(json, SmartHome.class);
    }

}
