package ru.sbt.mipt.oop;

public class Signalization {
    private State state;

    public void execute(SensorEvent event) {
        state.execute(event);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
