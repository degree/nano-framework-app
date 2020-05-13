package by.degree.learn.framework;

import by.degree.learn.framework.test.TestComponent;
import by.degree.learn.framework.test.TestPrimaryComponent;
import by.degree.learn.framework.test.TestSingletonComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaConfigTest {

    JavaConfig config = new JavaConfig("by.degree.learn.framework.test");

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
}
