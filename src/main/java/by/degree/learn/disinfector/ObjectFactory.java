package by.degree.learn.disinfector;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ObjectFactory {
    private static final ObjectFactory INSTANCE = new ObjectFactory();
    private final Reflections reflections;

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    private static final Map<Class, Object> CACHE = new ConcurrentHashMap<>();

    private final List<ObjectConfigurer> objectConfigurators;
    private final List<ProxyConfigurer> proxyConfigurers;

    private ObjectFactory() {
        reflections = new Reflections("by.degree.learn");
        objectConfigurators = load(ObjectConfigurer.class);
        proxyConfigurers = load(ProxyConfigurer.class);
    }

    private <T> List<T> load(Class<T> type) {
        return reflections.getSubTypesOf(type).stream()
                .map((Class<? extends T> implClass) -> (T) create(implClass)).collect(Collectors.toList());
    }

    public <T> T createObject(Class<T> target) {
        if (CACHE.containsKey(target)) {
            return (T) CACHE.get(target);
        }

        Class<? extends T> implClass = lookupImplementationClass(target);

        T t = create(implClass);

        configure(t);
        t = proxy(t, implClass);

        // todo support post-construct

        CACHE.put(target, t);

        return t;

        // todo implement singleton
    }

    private <T> T create(Class<T> implClass) {
        try {
            return implClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot instantiate ", e);
        }
    }

    private <T> Class<? extends T> lookupImplementationClass(Class<T> target) {
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
        return implClass;
    }

    private <T> void configure(T t) {
        for (ObjectConfigurer configurator : objectConfigurators) {
            configurator.configure(t);
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
