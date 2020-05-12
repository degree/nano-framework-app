package by.degree.learn.framework;

public interface ProxyConfigurer {
    Object wrapIfNeeded(Object t, Class implClass);
}
