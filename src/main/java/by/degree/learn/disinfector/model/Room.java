package by.degree.learn.disinfector.model;

public class Room {
    private final String name;

    public Room(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                '}';
    }
}
