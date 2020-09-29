package ru.sbt.mipt.oop;

import java.io.IOException;

public interface SmartHomeReader {
    public SmartHome readSmartHome(String source) throws IOException;
}
