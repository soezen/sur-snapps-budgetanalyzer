package sur.snapps.tests.database.module;

import org.unitils.core.Module;
import org.unitils.core.TestListener;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * User: SUR
 * Date: 4/05/14
 * Time: 15:53
 */
public class DatabaseModule implements Module {

    private DatabaseConfiguration configuration;

    @Override
    public void init(Properties properties) {
        this.configuration = new DatabaseConfiguration(properties);
    }

    @Override
    public void afterInit() {
    }

    @Override
    public TestListener getTestListener() {
        return new DatabaseTestListener();
    }

    protected class DatabaseTestListener extends TestListener {

        @Override
        public void beforeTestClass(Class<?> testClass) {
            super.beforeTestClass(testClass);
        }

        @Override
        public void beforeTestSetUp(Object testObject, Method testMethod) {
            super.beforeTestSetUp(testObject, testMethod);
        }

        @Override
        public void afterTestTearDown(Object testObject, Method testMethod) {
            super.afterTestTearDown(testObject, testMethod);
        }
    }
}
