package ru.sbt.mipt.oop;

public abstract class SignalizationState {
    protected final Signalization signalization;

    protected SignalizationState(Signalization signalization) {
        this.signalization = signalization;
    }

    public void activate(int code) {
        signalization.setState(new Activated(signalization, code));
    }

    public void deactivate() {
        signalization.setState(new Deactivated(signalization));
    }

    public void alarm() {
        signalization.setState(new Alarm(signalization));
    }
}
