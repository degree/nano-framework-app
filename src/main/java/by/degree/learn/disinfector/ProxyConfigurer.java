package by.degree.learn.disinfector;

public interface ProxyConfigurer {
    Object wrapIfNeeded(Object t, Class implClass);
}
