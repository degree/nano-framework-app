package by.degree.learn.disinfector;

import java.lang.reflect.Field;
import java.util.Map;

public class InjectPropertyConfigurator implements ObjectConfigurator {

    private Map<String, String> properties = Map.of("name", "Dredd");

    @Override
    public <T> void configure(T t) {
        for (Field field : t.getClass().getDeclaredFields()) {
            InjectProperty annotation = field.getDeclaredAnnotation(InjectProperty.class);
            if (annotation != null) {
                String property = annotation.value();
                if (property.isEmpty()) {
                    property = field.getName();
                }
                field.setAccessible(true);
                String value = properties.get(property);
                try {
                    field.set(t, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("cannot set field " + field.getName() + " with property " + property + " and value " + value);
                }
            }
        }
    }
}
