package by.degree.learn.disinfector;

public class Disinfector {
    public void disinfect(Room room) {
        announce("Announce disinfection: leave " + room.getName());
        enforce();
        doDisinfection(room);
        announce("Announce disinfection complete");
    }

    private void enforce() {
        System.out.println("Enforce!");
    }

    private void announce(String s) {
        System.out.println(s);
    }

    private void doDisinfection(Room room) {
        System.out.println("Disinfecting room " + room.getName());
    }

    public static void main(String[] args) {
        new Disinfector().disinfect(new Room("laboratory"));
    }
}
