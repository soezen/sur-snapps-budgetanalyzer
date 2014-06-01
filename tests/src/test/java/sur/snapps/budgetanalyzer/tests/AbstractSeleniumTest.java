package sur.snapps.budgetanalyzer.tests;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import sur.snapps.budgetanalyzer.tests.pages.DashboardPage;
import sur.snapps.budgetanalyzer.tests.pages.HomePage;
import sur.snapps.budgetanalyzer.tests.pages.LoginPage;
import sur.snapps.jetta.database.DatabaseTestRule;
import sur.snapps.jetta.selenium.annotations.SeleniumWebDriver;
import sur.snapps.jetta.selenium.elements.WebPage;
import sur.snapps.jetta.selenium.SauceTestWatcher;
import sur.snapps.jetta.selenium.SeleniumTestRule;

/**
 * User: SUR
 * Date: 27/04/14
 * Time: 14:24
 */
public abstract class AbstractSeleniumTest implements SauceOnDemandSessionIdProvider {

    @Rule
    public SauceTestWatcher sauceTestWatcher = new SauceTestWatcher(this);

    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule(this);

    @Rule
    public DatabaseTestRule databaseTestRule = new DatabaseTestRule(this);

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
