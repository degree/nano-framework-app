package by.degree.learn.disinfector;

public class Main {
    public static void main(String[] args) {
        var disinfector = ObjectFactory.getInstance().create(Disinfector.class);
        disinfector.disinfect(new Room("laboratory"));
    }
}
