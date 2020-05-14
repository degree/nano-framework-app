package by.degree.learn.disinfector.impl;

import by.degree.learn.disinfector.model.Enforcer;
import by.degree.learn.solid.framework.Component;
import by.degree.learn.solid.framework.InjectProperty;

@Component
public class DefaultEnforcer implements Enforcer {

    @InjectProperty("enforcer.default.name")
    private String name;

    @Override
    public void enforce() {
        System.out.println(name + ": Leave the room!");
    }
}
