package sur.snapps.tests.budgetanalyzer;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.unitils.UnitilsJUnit4TestClassRunner;
import sur.snapps.tests.budgetanalyzer.pages.DashboardPage;
import sur.snapps.tests.budgetanalyzer.pages.HomePage;
import sur.snapps.tests.budgetanalyzer.pages.LoginPage;
import sur.snapps.unitils.modules.selenium.SeleniumWebDriver;
import sur.snapps.unitils.modules.selenium.page.elements.WebPage;

/**
 * User: SUR
 * Date: 27/04/14
 * Time: 14:24
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public abstract class AbstractSeleniumTest implements SauceOnDemandSessionIdProvider {

    @Rule
    public SauceOnDemandTestWatcher testRule = new SauceOnDemandTestWatcher(this, true);

    private String sessionId;

    @SeleniumWebDriver
    protected WebDriver driver;


    @WebPage
    protected HomePage homePage;
    @WebPage
    protected LoginPage loginPage;
    @WebPage
    protected DashboardPage dashboardPage;

    @Before
    public void setUp() throws Exception {
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
