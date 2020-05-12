package by.degree.learn.disinfector.impl;

import by.degree.learn.disinfector.infrastructure.Announce;
import by.degree.learn.disinfector.model.Disinfector;
import by.degree.learn.disinfector.model.Enforcer;
import by.degree.learn.disinfector.model.Room;
import by.degree.learn.framework.Inject;
import by.degree.learn.framework.Singleton;

@Singleton
public class DisinfectorImpl implements Disinfector {
    @Inject
    private Enforcer enforcer;

    @Override
    @Announce
    public void disinfect(Room room) {
        enforcer.enforce();
        doDisinfection(room);
    }

    private void doDisinfection(Room room) {
        System.out.println("Disinfecting " + room);
    }
}
