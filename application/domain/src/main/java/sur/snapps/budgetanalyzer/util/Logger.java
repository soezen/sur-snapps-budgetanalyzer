package sur.snapps.budgetanalyzer.util;

import org.apache.logging.log4j.LogManager;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 21:54
 */
public final class Logger {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger("SUR-SNAPPS");

    private Logger() { }

    public static void info(String message) {
        LOG.info(message);
    }

    public static void error(String message) {
        LOG.error(message);
    }
}
