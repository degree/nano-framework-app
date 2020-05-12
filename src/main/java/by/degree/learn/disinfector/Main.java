package by.degree.learn.disinfector;

public class Main {
    public static void main(String[] args) {
        Context context = Application.run("by.degree.learn.disinfector");
        var disinfector = context.getObject(Disinfector.class);
        disinfector.disinfect(new Room("laboratory"));
    }
}
