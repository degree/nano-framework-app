package by.degree.learn.disinfector;

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
