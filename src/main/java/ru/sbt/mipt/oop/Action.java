package ru.sbt.mipt.oop;

import java.util.function.Function;

public class Action {
    private Function<HomeComponent, Void> action;

    Action(Function<HomeComponent, Void> action) {
        if (action == null) throw new IllegalArgumentException("Null input");
        this.action = action;
    }
    void execute(HomeComponent homeComponent) {
        action.apply(homeComponent);
    }
}
