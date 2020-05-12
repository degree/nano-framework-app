package by.degree.learn.disinfector;

public class Application {

    public static Context run(String basePackage) {
        Config config = new Config(basePackage);
        Context context = new Context(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }
}
