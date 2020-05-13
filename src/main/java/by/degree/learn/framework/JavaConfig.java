package by.degree.learn.framework;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaConfig implements Config {

    private final Reflections framework;
    private final Reflections application;

    public JavaConfig(String basePackage) {
        framework = new Reflections("by.degree.learn.framework");
        application = new Reflections(basePackage);
    }

    <T> boolean isPrimary(Class<? extends T> aClass) {
        return hasPrimaryAnnotation(aClass) || hasMetaAnnotation(aClass, this::hasPrimaryAnnotation);
    }

    <T> boolean hasMetaAnnotation(Class<? extends T> c, Predicate<Class<? extends Annotation>> hasAnnotation) {
        return Arrays.stream(c.getAnnotations())
                .flatMap(this::flatten)
                .map(Annotation::annotationType)
                .anyMatch(hasAnnotation);
    }

    Stream<Annotation> flatten(Annotation annotation) {
        return Stream.concat(
                Stream.of(annotation),
                Stream.of(annotation.annotationType().getAnnotations())
                        .filter(a -> !a.annotationType().isAnnotationPresent(a.annotationType()))
                        .filter(a -> !a.annotationType().getPackageName().startsWith("java.lang.annotation"))
                        .flatMap(this::flatten)
        );
    }

    @Override
    public <T> boolean isSingleton(Class<? extends T> aClass) {
        return hasSingletonAnnotation(aClass) || hasMetaAnnotation(aClass, this::hasSingletonAnnotation);
    }

    @Override
    public <T> boolean isComponent(Class<? extends T> aClass) {
        return hasComponentAnnotation(aClass) || hasMetaAnnotation(aClass, this::hasComponentAnnotation);
    }

    private <T> boolean hasComponentAnnotation(Class<? extends T> cl) {
        return cl.isAnnotationPresent(Component.class);
    }

    private <T> boolean hasSingletonAnnotation(Class<? extends T> cl) {
        return cl.isAnnotationPresent(Singleton.class);
    }

    private <T> boolean hasPrimaryAnnotation(Class<? extends T> c) {
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
        var builder = Stream.<Class<? extends T>>builder();
        if (!type.isInterface() && isComponent(type)) {
            builder.accept(type);
        }
        framework.getSubTypesOf(type).forEach(builder);
        application.getSubTypesOf(type).stream().filter(this::isComponent).forEach(builder);
        return builder.build().collect(Collectors.toList());
    }
}
