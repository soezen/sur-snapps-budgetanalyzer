package sur.snapps.budgetanalyzer.tests;

import com.google.common.base.Function;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import sur.snapps.budgetanalyzer.tests.pages.DashboardPage;
import sur.snapps.budgetanalyzer.tests.pages.LoginPage;
import sur.snapps.budgetanalyzer.tests.pages.Menu;
import sur.snapps.jetta.database.JettaDatabaseRule;
import sur.snapps.jetta.metadata.MetaDataTestRule;
import sur.snapps.jetta.metadata.MetaDataTestRunner;
import sur.snapps.jetta.selenium.SauceTestWatcher;
import sur.snapps.jetta.selenium.SeleniumTestRule;
import sur.snapps.jetta.selenium.annotations.SeleniumWebDriver;
import sur.snapps.jetta.selenium.elements.BaseUrl;
import sur.snapps.jetta.selenium.elements.WebPage;

import java.util.concurrent.TimeUnit;

/**
 * User: SUR
 * Date: 27/04/14
 * Time: 14:24
 */
@RunWith(MetaDataTestRunner.class)
public abstract class AbstractSeleniumTest implements SauceOnDemandSessionIdProvider {

    @Rule
    public SauceTestWatcher sauceTestWatcher = new SauceTestWatcher(this);

    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule(this);

    @Rule
    public JettaDatabaseRule databaseTestRule = new JettaDatabaseRule(this);

    @Rule
    public MetaDataTestRule metaDataTestRule = new MetaDataTestRule(this);

    private String sessionId;

    @SeleniumWebDriver
    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    @BaseUrl
    protected String baseUrl;

    @WebPage
    protected Menu menu;
    @WebPage
    protected LoginPage loginPage;
    @WebPage
    protected DashboardPage dashboardPage;

    @Before
    public void setUp() throws Exception {
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
        wait = new FluentWait<>(driver)
                .ignoring(NoSuchElementException.class)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .withTimeout(3, TimeUnit.SECONDS);

        driver.navigate().to(baseUrl + "/budgetanalyzer/homepage");
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    protected void assertEqualsWhileWaiting(final Object expected, final Object actual) {
        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return expected.equals(actual);
            }
        });
    }
}
