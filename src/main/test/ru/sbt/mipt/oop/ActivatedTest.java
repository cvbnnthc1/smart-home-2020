package ru.sbt.mipt.oop;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActivatedTest {

    @Test
    public void execute_changeFromActivatedToDeactivated_whenRightCode() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_DEACTIVATE,"123", 123);
        signalization.setState(new Activated(signalization, 123));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Deactivated);
    }

    @Test
    public void execute_changeFromActivatedToAlarm_whenWrongCode() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_DEACTIVATE,"123", 123);
        signalization.setState(new Activated(signalization, 234));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }

    @Test
    public void execute_changeFromActivatedToAlarm_whenActivatedEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_ACTIVATE,"123", 123);
        signalization.setState(new Activated(signalization, 234));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }

    @Test
    public void execute_changeFromActivatedToAlarm_whenAlarmCancellingEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_CANCELING,"123");
        signalization.setState(new Activated(signalization, 234));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }
}