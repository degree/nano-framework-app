package by.degree.learn.disinfector;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {
    private static final ObjectFactory INSTANCE = new ObjectFactory();

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    public <T> T create(Class<T> target) {
        Class<? extends T> implClass;
        if (target.isInterface()) {
            var reflections = new Reflections("by.degree.learn");
            var implementors = reflections.getSubTypesOf(target);

            // todo support Primary
            if (implementors.size() != 1) {
                throw new RuntimeException("Cannot instantiate " + target + ". It is implemented by " + implementors.size() + ". Must be exactly 1.");
            }

            implClass = implementors.iterator().next();
        } else {
            implClass = target;
        }

        try {
            // todo configure
            return implClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot instantiate ", e);
        }
    }
}
