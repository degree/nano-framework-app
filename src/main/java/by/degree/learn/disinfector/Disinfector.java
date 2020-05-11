package by.degree.learn.disinfector;

public class Disinfector {
    public void disinfect(Room room) {
        System.out.println("Announce disinfection: leave " + room.getName());
        System.out.println("Enforce!");
        System.out.println("Disinfecting root " + room.getName());
        System.out.println("Announce disinfection complete");
    }

    public static void main(String[] args) {
        new Disinfector().disinfect(new Room("laboratory"));
    }
}
