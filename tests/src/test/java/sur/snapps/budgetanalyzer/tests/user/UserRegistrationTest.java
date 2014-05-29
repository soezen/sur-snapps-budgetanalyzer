package sur.snapps.budgetanalyzer.tests.user;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Ignore;
import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.pages.user.UserRegistrationPage;
import sur.snapps.unitils.modules.database.Script;
import sur.snapps.unitils.modules.selenium.SeleniumTestCase;
import sur.snapps.unitils.modules.selenium.page.elements.WebPage;

import static org.junit.Assert.assertTrue;

/**
 * Test data: the A-Team
 *  - John Smith aka Hannibal
 *    -> already registered
 *  - Templeton Peck aka Face
 *    -> successfully registered as admin
 *  - Bosco Albert Baracus aka BA
 *    -> used for validation tests
 *  - HM Murdock aka Murdock
 *    -> successfully registered as non-admin
 */
// TODO make other webdrivers work
// TODO clear database + insert test data (not with dbmaintainer?)
@SeleniumTestCase("01 - User Registration")
@Script("users.sql")
public class UserRegistrationTest extends AbstractSeleniumTest {

    @WebPage
    private UserRegistrationPage userRegistrationPage;

    @Test
    public void success() {

    }

    @Test
    public void adminSuccess() {
        homePage.openUserDashboard();
        loginPage.openRegistrationPage();

        boolean registrationSuccess = userRegistrationPage
                .name("Templeton Peck")
                .username("face")
                .email("face@a-team.com")
                .password("TEST$test12")
                .register()
                .isSuccess();
        assertTrue("There were errors submitting the 'user registration' form.", registrationSuccess);

        homePage.openUserDashboard();
        boolean loginSuccess = loginPage.username("face")
                .password("TEST$test12")
                .login()
                .isSuccess();
        assertTrue("There were errors logging in.", loginSuccess);

        // TODO do database assertions
        // user created
        // entity created
        // authorities user and admin
    }

    @Test
    public void adminErrorInsecurePassword() {
        homePage.openUserDashboard();
        loginPage.openRegistrationPage();

        boolean passwordError = userRegistrationPage
                .name("Bosco Albert Baracus")
                .username("ba")
                .email("ba-baracus@a-team.com")
                .password("test")
                .register()
                .hasFieldError("password");
        assertTrue(passwordError);

        // TODO confirm user not twice in db
    }

    /**
     * Does not work because email validation by browser.
     */
    @Test
    @Ignore
    public void adminErrorInvalidEmail() {
        homePage.openUserDashboard();
        loginPage.openRegistrationPage();

        boolean emailError = userRegistrationPage
                .name("Bosco Albert Baracus")
                // TODO add validation to username: has to be one word (exclude special characters)
                .username("ba")
                .email("invalid")
                .password("TEST$test12")
                .register()
                .hasFieldError("email");
        assertTrue(emailError);

        // TODO confirm user not twice in db
    }

    @Test
    public void notAdminSuccess() {
        driver.get("http://localhost:2001/web/tests/userRegistrationWithToken?value=valid_token");

        boolean success = userRegistrationPage
                .name("HM Murdock")
                .username("murdock")
                .password("TEST$test12")
                .register()
                .isSuccess();
        assertTrue("There were errors submitting the 'user registration' form.", success);

        homePage.openUserDashboard();
        boolean loginSuccess = loginPage.username("murdock")
                .password("TEST$test12")
                .login()
                .isSuccess();
        assertTrue("There were errors logging in.", loginSuccess);

        // TODO database assertions
        // new user
        // not new entity
        // authorities only user
    }

    @Test
    public void adminErrorUsernameAlreadyUsed() {
        homePage.openUserDashboard();
        loginPage.openRegistrationPage();

        boolean usernameError = userRegistrationPage
                .name("John Smith")
                .username("hannibal")
                .email("hannibal@a-team.com")
                .password("TEST$test12")
                .register()
                .hasFieldError("username");
        assertTrue(usernameError);

        // TODO confirm user not twice in db
    }

    @Test
    public void notAdminErrorUsernameAlreadyUsed() {
        throw new NotImplementedException();
    }

    @Test
    public void notAdminErrorTokenExpired() {
        throw new NotImplementedException();
    }

    @Test
    public void notAdminErrorTokenRevoked() {
        throw new NotImplementedException();
    }

}
