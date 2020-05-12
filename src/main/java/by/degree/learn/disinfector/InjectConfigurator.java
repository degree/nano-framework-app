package by.degree.learn.disinfector;

import java.lang.reflect.Field;

public class InjectConfigurator implements ObjectConfigurer {
    @Override
    public <T> void configure(T t, Context context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object obj = context.getObject(field.getType());
                try {
                    field.set(t, obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot update " + t + "." + field.getName(), e);
                }
            }
        }
    }
}
