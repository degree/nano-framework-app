package by.degree.learn.disinfector.impl;

import by.degree.learn.disinfector.model.Enforcer;
import by.degree.learn.framework.InjectProperty;

public class DefaultEnforcer implements Enforcer {

    @InjectProperty("enforcer.default.name")
    private String name;

    @Override
    public void enforce() {
        System.out.println(name + ": Leave the room!");
    }
}