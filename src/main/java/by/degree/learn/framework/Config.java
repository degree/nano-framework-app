package by.degree.learn.framework;

import java.util.Collection;

public interface Config {
    <T> Class<? extends T> lookupImplementationClass(Class<T> target);

    <T> Collection<Class<? extends T>> listImplementations(Class<T> type);
}
