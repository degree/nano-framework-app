package by.degree.learn.framework;

import by.degree.learn.framework.test.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaConfigTest {

    JavaConfig config = new JavaConfig("by.degree.learn.framework.test");

    @Test
    void noComponentIsNotComponent() {
        assertFalse(config.isComponent(NoComponent.class), "Class without @Component must be not treated as component");
    }

    @Test
    void componentIsComponent() {
        assertTrue(config.isComponent(TestComponent.class), "Class annotated with @Component must be treated as component");
    }

    @Test
    void primaryIsComponent() {
        assertTrue(config.isComponent(TestPrimaryComponent.class), "Class annotated with @Primary must be treated as component");
    }

    @Test
    void singletonIsComponent() {
        assertTrue(config.isComponent(TestSingletonComponent.class), "Class annotated with @Singleton must be treated as component");
    }

    @Test
    void myComponentIsComponent() {
        assertTrue(config.isComponent(TestMyComponent.class), "Class annotated with annotation with @Component must be treated as component");
    }
}
