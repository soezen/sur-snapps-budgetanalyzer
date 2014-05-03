package sur.snapps.budgetanalyzer.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 21:54
 */
public final class Logger {

    private static final Log LOG = LogFactory.getLog("SUR-SNAPPS");

    private Logger() { }

    public static void info(String message) {
        LOG.info(message);
    }

    public static void error(String message) {
        LOG.error(message);
    }
}
