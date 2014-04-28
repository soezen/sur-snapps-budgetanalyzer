package sur.snapps.budgetanalyzer.tests;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * User: SUR
 * Date: 27/04/14
 * Time: 14:24
 */
public abstract class AbstractSeleniumTest implements SauceOnDemandSessionIdProvider {

    private SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("cloudbees_snapps", "f35becaf-bd5c-4e27-a615-86871f1582b8");

    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @Rule
    public TestName testName = new TestName();

    private String sessionId;


    protected WebDriver driver;

    protected abstract UseCase getUseCase();
    protected abstract void goToStartLocation();

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("version", "28");
        capabillities.setCapability("platform", Platform.WIN8);
        capabillities.setCapability("name", getUseCase().getName() + " : " + testName.getMethodName());
        capabillities.setCapability("tags", getUseCase().name());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();

        goToStartLocation();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
