package by.degree.learn.disinfector;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectFactory {
    private static final ObjectFactory INSTANCE = new ObjectFactory();
    private final Reflections reflections;

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    private static final Map<Class, Object> CACHE = new ConcurrentHashMap<>();

    private List<ObjectConfigurator> configurators;

    private ObjectFactory() {
        reflections = new Reflections("by.degree.learn");
        configurators = new ArrayList<>();
        for (Class<? extends ObjectConfigurator> oConfClass : reflections.getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(instantiate(oConfClass));
        }
    }

    public <T> T create(Class<T> target) {
        if (CACHE.containsKey(target)){
            return (T) CACHE.get(target);
        }
        T implementation = getImplementation(target);

        // todo implement singleton
        CACHE.put(target, implementation);
        return implementation;
    }

    private <T> T getImplementation(Class<T> target) {
        Class<? extends T> implClass;
        if (target.isInterface()) {
            var implementors = reflections.getSubTypesOf(target);

            // todo support Primary
            if (implementors.size() != 1) {
                throw new RuntimeException("Cannot instantiate " + target + ". It is implemented by " + implementors.size() + ". Must be exactly 1.");
            }

            implClass = implementors.iterator().next();
        } else {
            implClass = target;
        }

        return instantiate(implClass);
    }

    private <T> T instantiate(Class<? extends T> implClass) {
        try {
            System.out.println(">> Instantiate: " + implClass);
            T t = implClass.getConstructor().newInstance();

            // todo configure
            configure(t);

            // todo support post-construct

            return t;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot instantiate ", e);
        }
    }

    private <T> void configure(T t) {
        for (ObjectConfigurator configurator : configurators) {
            configurator.configure(t);
        }
    }
}
