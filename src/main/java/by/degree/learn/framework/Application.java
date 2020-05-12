package by.degree.learn.framework;

public class Application {

    public static Context run(String basePackage) {
        Config config = new JavaConfig(basePackage);
        Context context = new Context(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }
}
