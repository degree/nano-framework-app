package by.degree.learn.framework;

import by.degree.learn.framework.test.MySuperMetaComponent;
import by.degree.learn.framework.test.NoComponent;
import by.degree.learn.framework.test.TestMyMetaComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaConfigListImplementationTest {

    JavaConfig config = new JavaConfig("by.degree.learn.framework.test");

    @Test
    void listNoComponent() {
        assertTrue(config.listImplementations(NoComponent.class).isEmpty());
    }

    @Test
    void listMySuperMetaComponent() {
        assertEquals(1, config.listImplementations(TestMyMetaComponent.class).size(), "There must be one component found");
    }
}
