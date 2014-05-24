package sur.snapps.tests.budgetanalyzer;

import org.junit.Test;
import sur.snapps.tests.budgetanalyzer.pages.HomePage;
import sur.snapps.tests.budgetanalyzer.pages.LoginPage;
import sur.snapps.tests.budgetanalyzer.pages.UserRegistrationPage;
import sur.snapps.tests.selenium.module.SeleniumTestCase;
import sur.snapps.tests.selenium.module.WebPage;

import static org.junit.Assert.assertTrue;

// TODO make other webdrivers work
// TODO clear database + insert test data (not with dbmaintainer?)
@SeleniumTestCase("01 - User Registration")
public class UserRegistrationTest extends AbstractSeleniumTest {

    @WebPage
    private HomePage homePage;
    @WebPage
    private LoginPage loginPage;
    @WebPage
    private UserRegistrationPage userRegistrationPage;

    @Test
    public void userRegistration() throws Exception {
        homePage.openUserDashboard();
        loginPage.openRegistrationPage();

        boolean registrationSuccess = userRegistrationPage
                .name("Jean-Claude Van Damme")
                .username("jcvd")
                .email("rogge.suzan@gmail.com")
                .password("TEST$test12")
                .register()
                .isSuccess();
        assertTrue("There were errors submitting the 'user registration' form.", registrationSuccess);

        homePage.openUserDashboard();
        boolean loginSuccess = loginPage.username("jcvd")
                .password("TEST$test12")
                .login()
                .isSuccess();
        assertTrue("There were errors logging in.", loginSuccess);

        // TODO do database assertions
    }

    @Test
    public void userRegistrationWithExistingUsername() {
        homePage.openUserDashboard();
        loginPage.openRegistrationPage();

        // TODO replace with test data
        userRegistrationPage
                .name("Suzan Rogge")
                .username("sur")
                .email("rogge.suzan@gmail.com")
                .password("TEST$test12")
                .register()
                .hasFieldError("username");

        // TODO confirm user not twice in db
    }
}
