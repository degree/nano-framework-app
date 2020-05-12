package by.degree.learn.disinfector;

import org.reflections.Reflections;

import java.util.Collection;
import java.util.stream.Collectors;

public class JavaConfig implements Config {

    private final Reflections reflections;

    public JavaConfig(String basePackage) {
        reflections = new Reflections(basePackage);
    }

    private static <T> boolean isPrimary(Class<? extends T> c) {
        return c.isAnnotationPresent(Primary.class);
    }

    @Override
    public <T> Class<? extends T> lookupImplementationClass(Class<T> target) {
        Class<? extends T> implClass;
        if (target.isInterface()) {
            var implementors = listImplementations(target);

            if (implementors.isEmpty()) {
                throw new RuntimeException("Cannot instantiate " + target + ". There is no implementations.");
            } else if (implementors.size() > 1) {
                implementors = implementors.stream().filter(JavaConfig::isPrimary).collect(Collectors.toList());
                if (implementors.isEmpty()) {
                    throw new RuntimeException("Cannot instantiate " + target + ". No primary implementation.");
                } else if (implementors.size() > 1) {
                    throw new RuntimeException("Cannot instantiate " + target + ". Too many classes with @Primary.");
                }
            }

            implClass = implementors.iterator().next();
        } else {
            implClass = target;
        }
        return implClass;
    }

    @Override
    public <T> Collection<Class<? extends T>> listImplementations(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }
}
