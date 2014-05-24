package sur.snapps.tests.selenium.module;

import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 6:56
 */
public class SauceTestWatcher implements TestRule {

    private TestWatcher watcher;

    public void setReportWatcher(SauceOnDemandTestWatcher watcher) {
        this.watcher = watcher;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        System.out.println("HIER: " + watcher);
        return watcher.apply(base, description);
    }
}
