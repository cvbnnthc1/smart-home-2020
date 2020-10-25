package ru.sbt.mipt.oop;

public class Activated extends SignalizationState {
    private final int code;

    Activated(Signalization signalization, int code) {
        super(signalization);
        this.code = code;
    }

    boolean checkCode(int code) {
        return this.code == code;
    }
}
