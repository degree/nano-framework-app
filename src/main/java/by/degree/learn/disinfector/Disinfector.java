package by.degree.learn.disinfector;

public class Disinfector {
    @Inject
    private Enforcer enforcer;

    public void disinfect(Room room) {
        announce("Announce disinfection: leave " + room.getName());
        enforcer.enforce();
        doDisinfection(room);
        announce("Announce disinfection: complete " + room.getName());
    }

    private void announce(String s) {
        System.out.println(s);
    }

    private void doDisinfection(Room room) {
        System.out.println("Disinfecting room " + room.getName());
    }
}
