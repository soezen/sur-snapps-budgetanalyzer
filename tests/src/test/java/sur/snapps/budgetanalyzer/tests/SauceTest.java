package sur.snapps.budgetanalyzer.tests;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.unitils.util.ReflectionUtils;

import java.net.URL;

/**
 * User: SUR
 * Date: 29/05/14
 * Time: 15:49
 */
public class SauceTest implements SauceOnDemandSessionIdProvider {

        private SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("cloudbees_snapps", "f35becaf-bd5c-4e27-a615-86871f1582b8");

        @Rule
        public SauceOnDemandTestWatcher testWatcher = new SauceOnDemandTestWatcher(this, true);

        @Rule
        public TestName testName = new TestName();

        private String sessionId;


        protected WebDriver driver;


        @Before
        public void setUp() throws Exception {
            DesiredCapabilities capabillities = DesiredCapabilities.firefox();
            capabillities.setCapability("version", "28");
            capabillities.setCapability("platform", Platform.WIN8);
            capabillities.setCapability("name", testName.getMethodName());
            this.driver = new RemoteWebDriver(
                    new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
            this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
        }

    @Test
    public void succeed() throws NoSuchFieldException {
        SauceREST sauceRest = new SauceREST(authentication.getUsername(), authentication.getAccessKey());
        ReflectionUtils.setFieldValue(testWatcher, "sauceREST", sauceRest);
        // succeed
    }

    @Test
    public void fail() {
        Assert.fail();
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
