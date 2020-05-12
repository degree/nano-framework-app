package by.degree.learn.framework;

public interface ObjectConfigurer {
    <T> void configure(T t, Context context);
}
