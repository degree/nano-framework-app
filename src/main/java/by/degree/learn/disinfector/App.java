package by.degree.learn.disinfector;

import by.degree.learn.disinfector.model.Disinfector;
import by.degree.learn.disinfector.model.Room;
import by.degree.learn.framework.Application;
import by.degree.learn.framework.Context;

public class App {
    public static void main(String[] args) {
        Context context = Application.run("by.degree.learn");
        var disinfector = context.getObject(Disinfector.class);
        disinfector.disinfect(new Room("laboratory"));
    }
}
