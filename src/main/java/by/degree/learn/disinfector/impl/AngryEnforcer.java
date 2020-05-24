package by.degree.learn.disinfector.impl;

import by.degree.learn.disinfector.model.Enforcer;
import by.degree.learn.nano.framework.InjectProperty;
import by.degree.learn.nano.framework.PostConstruct;
import by.degree.learn.nano.framework.Primary;

@Primary
public class AngryEnforcer implements Enforcer {
    @InjectProperty("enforcer.enhanced.name")
    private String name;

    @Override
    public void enforce() {
        System.out.println(name + ": get out!");
    }

    @PostConstruct
    public void init() {
        System.out.println(name + " is getting angry");
    }
}
