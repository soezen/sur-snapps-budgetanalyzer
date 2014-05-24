package sur.snapps.tests.budgetanalyzer;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.unitils.UnitilsJUnit4TestClassRunner;
import sur.snapps.tests.selenium.module.SauceTestWatcher;
import sur.snapps.tests.selenium.module.SeleniumWebDriver;

/**
 * User: SUR
 * Date: 27/04/14
 * Time: 14:24
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public abstract class AbstractSeleniumTest implements SauceOnDemandSessionIdProvider {

    @Rule
    public SauceTestWatcher testRule = new SauceTestWatcher();

    private String sessionId;

    @SeleniumWebDriver
    protected WebDriver driver;


    @Before
    public void setUp() throws Exception {
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
    }

    @Override
    public String getSessionId() {
        System.out.println("GET SESSION ID: " + sessionId);
        return sessionId;
    }
}
