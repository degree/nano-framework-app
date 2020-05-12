package by.degree.learn.disinfector;

@Primary
public class AngryEnforcer implements Enforcer {
    @InjectProperty("enforcer.enhanced.name")
    private String name;

    @Override
    public void enforce() {
        System.out.println(name + ": get out!");
    }
}
