package ru.sbt.mipt.oop;

import java.util.function.Function;

public interface Actionable {
    boolean execute(Function<Actionable, Boolean> action);
}
