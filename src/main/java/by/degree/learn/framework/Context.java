package by.degree.learn.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {
    @SuppressWarnings("rawtypes")
    private final Map<Class, Object> CACHE = new ConcurrentHashMap<>();
    private final Config config;
    private ObjectFactory factory;

    public Context(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> target) {
        if (CACHE.containsKey(target)) {
            //noinspection unchecked
            return (T) CACHE.get(target);
        }

        Class<? extends T> implClass = config.lookupImplementationClass(target);

        T object = factory.createObject(implClass);

        if (config.isSingleton(implClass)) {
            CACHE.put(target, object);
        }

        return object;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    public Config getConfig() {
        return config;
    }
}
