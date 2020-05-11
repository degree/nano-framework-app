package by.degree.learn.disinfector;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ObjectFactory {
    private static final ObjectFactory INSTANCE = new ObjectFactory();

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    public <T> T create(Class<T> target) {
        var reflections = new Reflections("by.degree.learn");
        var implementors = reflections.getSubTypesOf(target);

        // todo support Primary
        if (implementors.size() != 1) {
            throw new RuntimeException("Cannot instantiate " + target + ". It is implemented by " + implementors.size() + ". Must be exactly 1.");
        }

        Class<? extends T> impl = implementors.iterator().next();
        try {
            // todo configure
            return impl.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot instantiate ", e);
        }
    }
}
