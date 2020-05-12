package by.degree.learn.disinfector;

import java.util.Collection;

public interface Config {
    <T> Class<? extends T> lookupImplementationClass(Class<T> target);

    <T> Collection<Class<? extends T>> listImplementations(Class<T> type);
}
