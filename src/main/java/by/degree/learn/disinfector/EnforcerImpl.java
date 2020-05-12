package by.degree.learn.disinfector;

public class EnforcerImpl implements Enforcer {

    @InjectProperty("enforcer.name")
    private String name;

    @Override
    public void enforce() {
        System.out.println(name + ": Leave the room!");
    }
}
