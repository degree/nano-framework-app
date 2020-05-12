package by.degree.learn.disinfector;

public class DefaultEnforcer implements Enforcer {

    @InjectProperty("enforcer.default.name")
    private String name;

    @Override
    public void enforce() {
        System.out.println(name + ": Leave the room!");
    }
}
