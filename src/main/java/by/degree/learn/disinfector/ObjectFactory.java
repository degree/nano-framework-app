package by.degree.learn.disinfector;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurer> objectConfigurators;
    private final List<ProxyConfigurer> proxyConfigurers;

    public ObjectFactory(Context context) {
        this.context = context;
        objectConfigurators = load(ObjectConfigurer.class);
        proxyConfigurers = load(ProxyConfigurer.class);
    }

    private <T> List<T> load(Class<T> type) {
        return context.getConfig().listImplementations(type).stream().map(this::create).collect(Collectors.toList());
    }

    public <T> T createObject(Class<T> implClass) {
        T t = create(implClass);

        configure(t);

        // todo support post-construct

        t = proxy(t, implClass);

        return t;
    }

    private <T> T create(Class<T> implClass) {
        try {
            return implClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot instantiate ", e);
        }
    }


    private <T> void configure(T t) {
        for (ObjectConfigurer configurator : objectConfigurators) {
            configurator.configure(t, context);
        }
    }

    private <T> T proxy(T t, Class<? extends T> implClass) {
        T result = t;
        for (ProxyConfigurer proxyConfigurer : proxyConfigurers) {
            result = (T) proxyConfigurer.wrapIfNeeded(t, implClass);
        }
        return result;
    }
}
