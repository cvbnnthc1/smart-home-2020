package ru.sbt.mipt.oop;

import java.util.function.Function;

public interface Actionable {
    boolean execute(Function<HomeComponent, Boolean> action);
}
