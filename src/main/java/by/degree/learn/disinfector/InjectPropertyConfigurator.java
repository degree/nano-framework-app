package by.degree.learn.disinfector;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class InjectPropertyConfigurator implements ObjectConfigurer {

    private final Map<String, String> properties;

    public InjectPropertyConfigurator() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (is != null) {
            try {
                Properties fileProperties = new Properties();
                fileProperties.load(is);
                properties = fileProperties.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
            } catch (IOException e) {
                throw new RuntimeException("Cannot load properties", e);
            }
        } else {
            properties = Collections.emptyMap();
        }
    }

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
