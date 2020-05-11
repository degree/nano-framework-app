package by.degree.learn.disinfector;

public class EnforcerImpl implements Enforcer {

    @InjectProperty
    private String name;

    @Override
    public void enforce() {
        System.out.println(name + ": Leave the room!");
    }
}
