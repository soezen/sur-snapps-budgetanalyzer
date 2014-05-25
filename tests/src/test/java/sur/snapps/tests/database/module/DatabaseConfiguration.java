package sur.snapps.tests.database.module;

import java.util.Properties;

import static org.unitils.util.PropertyUtils.getString;

/**
 * User: SUR
 * Date: 4/05/14
 * Time: 18:57
 */
public class DatabaseConfiguration {

    private static final String PREFIX = "sur.snapps.module.database.";
    private static final String DROP_SCRIPT = PREFIX + "drop_script";

    private String dropScript;

    public DatabaseConfiguration(Properties properties) {
        dropScript = getString(DROP_SCRIPT, properties);
    }

    public String dropScript() {
        return dropScript;
    }
}
