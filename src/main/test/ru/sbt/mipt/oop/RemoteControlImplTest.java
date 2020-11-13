package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import rc.RemoteControl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RemoteControlImplTest {
    RemoteControl remoteControl;
    AbstractApplicationContext context;

    @Before
    public void initiate() {
        context = new AnnotationConfigApplicationContext(RemoteControlConfiguration.class);
        remoteControl = context.getBean(RemoteControl.class);
    }

    @Test
    public void onButtonPressed_whenAllLightsOff_thenAllLightsOff() {
        //given
        List<String> ids = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            ids.add("" + i);
        }
        SmartHome smartHome = context.getBean(SmartHome.class);
        //when
        remoteControl.onButtonPressed("C");
        boolean flag = true;
        for (String id: ids) {
            flag = flag && smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    if (!light.isOn() && light.getId().equals(id)) {
                        return true;
                    }
                }
                return false;
            });
        }
        //then
        assertTrue(flag);
    }

    @Test
    public void onButtonPressed_whenHallLightsOn_thenHallLightsOn() {
        //given
        List<String> ids = new ArrayList<>();
        for (int i = 7; i <= 9; i++) {
            ids.add("" + i);
        }
        SmartHome smartHome = context.getBean(SmartHome.class);
        //when
        remoteControl.onButtonPressed("2");
        boolean flag = true;
        for (String id: ids) {
            flag = flag && smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    if (light.isOn() && light.getId().equals(id)) {
                        return true;
                    }
                }
                return false;
            });
        }
        //then
        assertTrue(flag);
    }

    @Test
    public void onButtonPressed_whenAllLightsOn_thenAllLightsOn() {
        //given
        List<String> ids = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            ids.add("" + i);
        }
        SmartHome smartHome = context.getBean(SmartHome.class);
        //when
        remoteControl.onButtonPressed("D");
        boolean flag = true;
        for (String id: ids) {
            flag = flag && smartHome.execute(s -> {
                if (s instanceof Light) {
                    Light light = (Light) s;
                    if (light.isOn() && light.getId().equals(id)) {
                        return true;
                    }
                }
                return false;
            });
        }
        //then
        assertTrue(flag);
    }

    @Test
    public void onButtonPressed_whenFrontDoorClosed_thenFrontDoorClosed() {
        //given
        List<String> ids = new ArrayList<>();
        ids.add("4");
        SmartHome smartHome = context.getBean(SmartHome.class);
        //when
        remoteControl.onButtonPressed("1");
        boolean flag = true;
        for (String id: ids) {
            flag = flag && smartHome.execute(s -> {
                if (s instanceof Door) {
                    Door door = (Door) s;
                    if (!door.isOpen() && door.getId().equals(id)) {
                        return true;
                    }
                }
                return false;
            });
        }
        //then
        assertTrue(flag);
    }

    @Test
    public void onButtonPressed_whenActivateSignalization_thenActivateSignalization() {
        //given
        Signalization signalization = context.getBean(Signalization.class);
        //when
        remoteControl.onButtonPressed("A");
        //then
        assertTrue(signalization.getState() instanceof Activated);
    }

    @Test
    public void onButtonPressed_whenAlarmOn_thenAlarmOn() {
        //given
        Signalization signalization = context.getBean(Signalization.class);
        //when
        remoteControl.onButtonPressed("B");
        //then
        assertTrue(signalization.getState() instanceof Alarm);
    }

}