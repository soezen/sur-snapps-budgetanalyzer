package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.FormResponse;
import sur.snapps.budgetanalyzer.tests.pages.user.UserRegistrationPage;
import sur.snapps.jetta.database.counter.RecordCounter;
import sur.snapps.jetta.database.counter.table.Table;
import sur.snapps.jetta.database.script.Script;
import sur.snapps.jetta.selenium.annotations.SeleniumTestCase;
import sur.snapps.jetta.selenium.elements.WebPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sur.snapps.jetta.database.counter.expression.conditional.Conditionals.equal;
import static sur.snapps.jetta.database.counter.expression.conditional.Conditionals.isNull;
import static sur.snapps.jetta.database.counter.expression.operator.Operators.and;
import static sur.snapps.jetta.database.counter.expression.operator.Operators.not;

/**
 * Test data: the A-Team
 *  - John Smith aka Hannibal
 *    -> already registered as admin
 *  - Templeton Peck aka Face
 *    -> already registered as not-admin
 */
// TODO-TECH make other webdrivers work
@SeleniumTestCase("01 - User Registration")
@Script("users.sql")
public class UserRegistrationTest extends AbstractSeleniumTest {

    @WebPage
    private UserRegistrationPage userRegistrationPage;

    private RecordCounter recordCounter;

    // TODO-FUNC UC-1 minimum length of username?

    @Test
    public void adminSuccess() {
        menu.register();

        boolean registrationSuccess = userRegistrationPage
                .name("Bosco Albert Baracus")
                .username("ba")
                .email("ba@a-team.com")
                .newPassword("TEST$test12")
                .confirmPassword("TEST$test12")
                .register()
                .isSuccess();
        assertTrue("There were errors submitting the 'user registration' form.", registrationSuccess);

        menu.login();
        boolean loginSuccess = loginPage.username("ba")
                .password("TEST$test12")
                .login()
                .isSuccess();
        assertTrue("There were errors logging in.", loginSuccess);


        // TODO-TECH add true and false methods
        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, recordCounter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'ba'"),
                        equal(usersTable.column("name"), "'Bosco Albert Baracus'"),
                        not(isNull(usersTable.column("password"))),
                        equal(usersTable.column("email"), "'ba@a-team.com'"),
                        equal(usersTable.column("enabled"), "1")))
                .get());

        // TODO let mother keep track of entities and verify that database has correct values
        Table entitiesTable = new Table("entities", "e", "id");
        entitiesTable.join(usersTable, "entity_id");
        assertEquals(1, recordCounter.count()
                .from(entitiesTable)
                .where(and(
                        equal(usersTable.column("username"), "'ba'"),
                        equal(entitiesTable.column("name"), "'Bosco Albert Baracus'"),
                        equal(entitiesTable.column("owned"), "1"),
                        equal(entitiesTable.column("shared"), "0")))
                .get());

        Table authoritiesTable = new Table("authorities", "a", "user_id");
        usersTable.join(authoritiesTable, "user_id");
        assertEquals(1, recordCounter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'ba'"),
                        equal(authoritiesTable.column("authority"), "'ROLE_USER'")))
                .get());
        assertEquals(1, recordCounter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'ba'"),
                        equal(authoritiesTable.column("authority"), "'ROLE_ADMIN'")))
                .get());
    }

    @Test
    public void adminErrorIncomplete() {
        menu.register();

        FormResponse response = userRegistrationPage.register();

        assertFalse(response.isSuccess());
        assertTrue(response.hasFieldError("name"));
        assertTrue(response.hasFieldError("username"));
        assertTrue(response.hasFieldError("email"));
        assertTrue(response.hasFieldError("newPassword"));
        assertTrue(response.hasFieldError("confirmPassword"));

        Table usersTable = new Table("users", "u", "id");
        assertEquals(0, recordCounter.count()
                .from(usersTable)
                .where(equal(usersTable.column("username"), "'ba'"))
                .get());
    }

    @Test
    public void adminErrorPasswordsNotMatching() {
        menu.register();

        FormResponse response = userRegistrationPage
                .name("Bosco Albert Baracus")
                .username("ba")
                .email("ba@a-team.com")
                .newPassword("TEST$test12")
                .confirmPassword("TEST$$test12")
                .register();

        assertFalse(response.isSuccess());
        assertTrue(response.hasFieldError("confirmPassword"));

        Table usersTable = new Table("users", "u", "id");
        assertEquals(0, recordCounter.count()
                .from(usersTable)
                .where(equal(usersTable.column("username"), "'ba'"))
                .get());
    }

    @Test
    public void adminErrorInsecurePassword() {
        menu.register();

        boolean passwordError = userRegistrationPage
                .name("Bosco Albert Baracus")
                .username("ba")
                .email("ba-baracus@a-team.com")
                .newPassword("test")
                .confirmPassword("test")
                .register()
                .hasFieldError("newPassword");
        assertTrue(passwordError);

        Table usersTable = new Table("users", "u", "id");
        assertEquals(0, recordCounter.count()
                .from(usersTable)
                .where(equal(usersTable.column("username"), "'ba'"))
                .get());
    }

    /**
     * Does not work because email validation by browser.
     */
    @Test
    @Ignore
    public void adminErrorInvalidEmail() {
        menu.register();

        boolean emailError = userRegistrationPage
                .name("Bosco Albert Baracus")
                // TODO add validation to username: has to be one word (exclude special characters)
                .username("ba")
                .email("invalid")
                .newPassword("TEST$test12")
                .confirmPassword("TEST$test12")
                .register()
                .hasFieldError("email");
        assertTrue(emailError);

        Table usersTable = new Table("users", "u", "id");
        assertEquals(0, recordCounter.count()
                .from(usersTable)
                .where(equal(usersTable.column("username"), "'ba'"))
                .get());
    }

    @Test
    public void notAdminSuccess() {
        driver.navigate().to("http://localhost:2001/web/budgetanalyzer/userRegistrationWithToken?value=token-valid");

        boolean success = userRegistrationPage
                .name("Bosco Albert Baracus")
                .username("ba")
                .newPassword("TEST$test12")
                .confirmPassword("TEST$test12")
                .register()
                .isSuccess();
        assertTrue("There were errors submitting the 'user registration' form.", success);

        menu.login();
        boolean loginSuccess = loginPage.username("ba")
                .password("TEST$test12")
                .login()
                .isSuccess();
        assertTrue("There were errors logging in.", loginSuccess);

        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, recordCounter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'ba'"),
                        equal(usersTable.column("name"), "'Bosco Albert Baracus'"),
                        not(isNull(usersTable.column("password"))),
                        equal(usersTable.column("email"), "'valid@test.com'"),
                        equal(usersTable.column("enabled"), "1"),
                        equal(usersTable.column("entity_id"), "1")))
                .get());

        Table authoritiesTable = new Table("authorities", "a", "user_id");
        usersTable.join(authoritiesTable, "user_id");
        assertEquals(1, recordCounter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'ba'"),
                        equal(authoritiesTable.column("authority"), "'ROLE_USER'")))
                .get());
        assertEquals(0, recordCounter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'ba'"),
                        equal(authoritiesTable.column("authority"), "'ROLE_ADMIN'")))
                .get());

        Table tokensTable = new Table("tokens", "t", "id");
        assertEquals(0, recordCounter.count()
                .from(tokensTable)
                .where(equal(tokensTable.column("value"), "'token-valid'"))
                .get());
    }

    @Test
    public void adminErrorUsernameAlreadyUsed() {
        menu.register();

        boolean usernameError = userRegistrationPage
                .name("John Smith")
                .username("hannibal")
                .email("hannibal@a-team.com")
                .newPassword("TEST$test12")
                .confirmPassword("TEST$test12")
                .register()
                .hasFieldError("username");
        assertTrue(usernameError);

        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, recordCounter.count()
                .from(usersTable)
                .where(equal(usersTable.column("username"), "'hannibal'"))
                .get());
    }

    @Test
    public void notAdminErrorUsernameAlreadyUsed() {
        driver.navigate().to("http://localhost:2001/web/budgetanalyzer/userRegistrationWithToken?value=token-valid");

        boolean usernameError = userRegistrationPage
                .name("John Smith")
                .username("hannibal")
                .email("hannibal@a-team.com")
                .newPassword("TEST$test12")
                .confirmPassword("TEST$test12")
                .register()
                .hasFieldError("username");
        assertTrue(usernameError);


        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, recordCounter.count()
                .from(usersTable)
                .where(equal(usersTable.column("username"), "'hannibal'"))
                .get());

        Table tokensTable = new Table("tokens", "t", "id");
        assertEquals(1, recordCounter.count()
                .from(tokensTable)
                .where(equal(tokensTable.column("value"), "'token-valid'"))
                .get());
    }

    @Test
    public void notAdminErrorTokenExpired() {
        driver.navigate().to("http://localhost:2001/web/budgetanalyzer/userRegistrationWithToken?value=token-expired");

        assertTrue(driver.findElement(By.id("form_error")).getText().contains("User invitation has expired."));
    }

    @Test
    public void notAdminErrorTokenRevoked() {
        driver.navigate().to("http://localhost:2001/web/budgetanalyzer/userRegistrationWithToken?value=token-revoked");

        assertTrue(driver.findElement(By.id("form_error")).getText().contains("User invitation has been revoked."));
    }

    @Test
    public void notAdminErrorTokenNotInDB() {
        driver.navigate().to("http://localhost:2001/web/budgetanalyzer/userRegistrationWithToken?value=token-not-existing");

        assertTrue(driver.findElement(By.id("form_error")).getText().contains("User invitation not found for this link."));
    }
}
