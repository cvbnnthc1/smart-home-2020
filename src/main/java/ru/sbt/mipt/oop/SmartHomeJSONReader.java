package ru.sbt.mipt.oop;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartHomeJSONReader implements SmartHomeReader{

    public  SmartHome readSmartHome(String source) throws IOException {
        Gson gson = new Gson();
        String json = new String(Files.readAllBytes(Paths.get(source)));
        return gson.fromJson(json, SmartHome.class);
    }

}
