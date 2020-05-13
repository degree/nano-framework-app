package by.degree.learn.framework;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaConfig implements Config {

    private final Reflections reflections;

    public JavaConfig(String basePackage) {
        reflections = new Reflections(basePackage);
    }

    <T> boolean isPrimary(Class<? extends T> c) {
        return c.isAnnotationPresent(Primary.class);
    }

    <T> boolean hasMetaAnnotation(Class<? extends T> c) {
        return Arrays.stream(c.getAnnotations())
                .flatMap(this::flatten)
                .map(Annotation::annotationType)
                .anyMatch(annotation -> annotation.isAnnotationPresent(Component.class));
    }

    Stream<Annotation> flatten(Annotation annotation) {
        return Stream.concat(Stream.of(annotation), Stream.of(annotation.annotationType().getAnnotations()));
//        return Stream.concat(Stream.of(annotation), Stream.of(annotation.annotationType().getAnnotations()).flatMap(this::flatten));
    }

    @Override
    public <T> boolean isSingleton(Class<? extends T> c) {
        return c.isAnnotationPresent(Singleton.class);
    }

    @Override
    public <T> boolean isComponent(Class<? extends T> c) {
        return Stream.<Predicate<Class<? extends T>>>of(
                this::hasComponentAnnotation,
                this::isSingleton,
                this::isPrimary,
                this::hasMetaAnnotation
        )
                .anyMatch(f -> f.test(c));
    }

    private <T> boolean hasComponentAnnotation(Class<? extends T> cl) {
        return cl.isAnnotationPresent(Component.class);
    }

    @Override
    public <T> Class<? extends T> lookupImplementationClass(Class<T> target) {
        Class<? extends T> implClass;
        if (target.isInterface()) {
            var implementors = listImplementations(target).stream()
                    .filter(this::isComponent)
                    .collect(Collectors.toList());

            if (implementors.isEmpty()) {
                throw new RuntimeException("Cannot instantiate " + target + ". There is no implementations.");
            } else if (implementors.size() > 1) {
                implementors = implementors.stream()
                        .filter(this::isPrimary)
                        .collect(Collectors.toList());
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
