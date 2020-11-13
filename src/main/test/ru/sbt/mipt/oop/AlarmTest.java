package ru.sbt.mipt.oop;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlarmTest {

    @Test
    public void execute_notChangeFromAlarm_whenActivatedEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_ACTIVATE,"123", 123);
        signalization.setState(new Alarm(signalization));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }

    @Test
    public void execute_notChangeFromAlarm_whenDeactivatedEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_DEACTIVATE,"123", 123);
        signalization.setState(new Alarm(signalization));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }

    @Test
    public void execute_changeFromAlarmToDeactivated_whenAlarmCancellingEvent() {
        //given
        Signalization signalization = new Signalization();
        SensorEvent event = new SensorEvent(SensorEventType.ALARM_CANCELING,"123");
        signalization.setState(new Alarm(signalization));
        //when
        signalization.execute(event);
        //then
        assertTrue(signalization.getState() instanceof Deactivated);
    }
}