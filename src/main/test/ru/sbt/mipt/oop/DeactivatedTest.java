package ru.sbt.mipt.oop;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeactivatedTest {

    @Test
    public void execute_changeFromDeactivatedToActivated_whenActivatedEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_ACTIVATE,"123", 123);
        signalization.setState(new Deactivated(signalization));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Activated);
    }

    @Test
    public void execute_changeFromDeactivatedToAlarm_whenDeactivatedEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_DEACTIVATE,"123", 123);
        signalization.setState(new Deactivated(signalization));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }

    @Test
    public void execute_changeFromDeactivatedToAlarm_whenAlarmCancellingEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_CANCELING,"123");
        signalization.setState(new Deactivated(signalization));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }
}