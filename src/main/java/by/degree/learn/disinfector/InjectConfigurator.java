package by.degree.learn.disinfector;

import java.lang.reflect.Field;

public class InjectConfigurator implements ObjectConfigurator {
    @Override
    public <T> void configure(T t) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object obj = ObjectFactory.getInstance().createObject(field.getType());
                try {
                    field.set(t, obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot update " + t + "." + field.getName(), e);
                }
            }
        }
    }
}
