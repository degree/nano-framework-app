package by.degree.learn.disinfector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AnnouncerProxy implements ProxyConfigurer {
    @Override
    public Object wrapIfNeeded(Object t, Class implClass) {

        boolean hasMethods = Arrays.stream(implClass.getDeclaredMethods()).anyMatch(AnnouncerProxy::hasAnnotation);

        if (hasMethods) {
            if (implClass.getInterfaces().length != 0) {
                return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), buildHandler(t));
            } else {
                throw new RuntimeException("Classes are not supported: " + implClass);
            }
        } else {
            return t;
        }
    }

    private static boolean hasAnnotation(Method method) {
        return method.isAnnotationPresent(Announce.class);
    }

    private InvocationHandler buildHandler(Object t) {
        return (proxy, method, args) -> {
            boolean wrap = method.isAnnotationPresent(Announce.class);
            if (wrap) {
                System.out.println("Announce disinfection: leave " + render(args));
            }

            Object result = method.invoke(t, args);

            if (wrap) {
                System.out.println("Announce disinfection: complete " + render(args));
            }
            return result;
        };
    }

    private static String render(Object... args) {
        return Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
    }
}
