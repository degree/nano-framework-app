package by.degree.learn.disinfector;

public class Disinfector {
    public void disinfect(Room room) {
        System.out.println("Disinfecting root " + room.getName());
    }

    public static void main(String[] args) {
        new Disinfector().disinfect(new Room("laboratory"));
    }
}
