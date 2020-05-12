package by.degree.learn.disinfector;

import org.reflections.Reflections;

import java.util.Collection;

public class Config {

    private final Reflections reflections;

    public Config(String basePackage) {
        reflections = new Reflections(basePackage);
    }

    public <T> Class<? extends T> lookupImplementationClass(Class<T> target) {
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

    public <T> Collection<Class<? extends T>> listImplementations(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }
}
