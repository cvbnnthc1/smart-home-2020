package ru.sbt.mipt.oop;

public class Signalization {
    private SignalizationState signalizationState;

    public void execute(SensorEvent event) {
        if (signalizationState != null) {
            signalizationState.handleEvent(event);
        }
    }

    public SignalizationState getState() {
        return signalizationState;
    }

    void setState(SignalizationState signalizationState) {
        this.signalizationState = signalizationState;
    }
}
