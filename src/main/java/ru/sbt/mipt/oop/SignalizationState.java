package ru.sbt.mipt.oop;

public abstract class SignalizationState {
    protected final Signalization signalization;

    public abstract void handleEvent(SensorEvent event);

    protected SignalizationState(Signalization signalization) {
        this.signalization = signalization;
    }

    protected void activate(int code) {
        signalization.setState(new Activated(signalization, code));
    }

    protected void deactivate() {
        signalization.setState(new Deactivated(signalization));
    }

    protected void alarm() {
        signalization.setState(new Alarm(signalization));
    }
}
