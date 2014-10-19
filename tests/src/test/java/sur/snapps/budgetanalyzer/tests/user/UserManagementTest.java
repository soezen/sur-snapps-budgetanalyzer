package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.pages.user.ProfilePage;
import sur.snapps.jetta.database.counter.RecordCounter;
import sur.snapps.jetta.database.counter.table.Table;
import sur.snapps.jetta.database.script.Script;
import sur.snapps.jetta.metadata.annotations.Pending;
import sur.snapps.jetta.metadata.annotations.Scenario;
import sur.snapps.jetta.metadata.annotations.UseCase;
import sur.snapps.jetta.metadata.xml.FailureImpact;
import sur.snapps.jetta.metadata.xml.ScenarioType;
import sur.snapps.jetta.selenium.annotations.SeleniumTestCase;
import sur.snapps.jetta.selenium.elements.WebPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static sur.snapps.budgetanalyzer.tests.dummy.Users.hannibal;
import static sur.snapps.jetta.database.counter.expression.conditional.Conditionals.equal;
import static sur.snapps.jetta.database.counter.expression.operation.Operations.and;

/**
 * User: SUR
 * Date: 5/07/14
 * Time: 15:26
 */
@SeleniumTestCase("02 - User Profile")
@Script("users.sql")
@UseCase("Manage User")
public class UserManagementTest extends AbstractSeleniumTest {

    private RecordCounter counter;

    @WebPage
    private ProfilePage profilePage;

    @Test
    @Scenario(type = ScenarioType.SUCCESS, failureImpact = FailureImpact.MEDIUM)
    public void editNameSuccess() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editName("Smith John");
        assertTrue(profilePage.isActionSuccess());
        assertFalse(profilePage.hasFormError());

        assertEquals("Smith John", profilePage.getName());

        // TODO simplify this!
        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, counter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'hannibal'"),
                        equal(usersTable.column("name"), "'Smith John'")))
                .get());
        assertEquals(0, counter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'hannibal'"),
                        equal(usersTable.column("name"), "'John Smith'")))
                .get());
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editNameEmpty() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editName("");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        assertEquals("John Smith", profilePage.getName());

        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, counter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'hannibal'"),
                        equal(usersTable.column("name"), "'John Smith'")))
                .get());

    }

    @Test
    @Scenario(type = ScenarioType.SUCCESS, failureImpact = FailureImpact.MEDIUM)
    public void editEmailSuccess() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editEmail("other@mail.com");
        assertTrue(profilePage.isActionSuccess());
        assertFalse(profilePage.hasFormError());

        assertEquals("other@mail.com", profilePage.getEmail());

        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, counter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'hannibal'"),
                        equal(usersTable.column("email"), "'other@mail.com'")))
                .get());
        assertEquals(0, counter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'hannibal'"),
                        equal(usersTable.column("email"), "'hannibal@a-team.com'")))
                .get());
    }

    @Test
    @Pending
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.LOW)
    public void cancelEditName() {
        fail("not yet implemented");
    }

    // TODO when session timed out and action is performed: give correct message

    // TODO when revoking an invitation set date to today

    @Test
    @Pending
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.LOW)
    public void cancelEditEmail() {
        fail("not yet implemented");
    }

    @Test
    @Pending
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.LOW)
    public void cancelEditPassword() {
        fail("not yet implemented");
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editEmailEmpty() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editEmail("");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        assertEquals(hannibal().email(), profilePage.getEmail());

        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, counter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'hannibal'"),
                        equal(usersTable.column("email"), "'hannibal@a-team.com'")))
                .get());
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editEmailInvalid() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editEmail("email @invalid.com");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        assertEquals(hannibal().email(), profilePage.getEmail());

        Table usersTable = new Table("users", "u", "id");
        assertEquals(1, counter.count()
                .from(usersTable)
                .where(and(
                        equal(usersTable.column("username"), "'hannibal'"),
                        equal(usersTable.column("email"), "'hannibal@a-team.com'")))
                .get());
    }

    @Test
    @Scenario(type = ScenarioType.SUCCESS, failureImpact = FailureImpact.MEDIUM)
    public void editPasswordSuccess() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editPassword("TEST$test12", "TEST$test12");
        assertTrue(profilePage.isActionSuccess());
        assertFalse(profilePage.hasFormError());

        assertEquals("**********", profilePage.getPassword());

        menu.logout();
        menu.login();
        assertFalse(loginPage.login(hannibal()).isSuccess());
        loginPage.username(hannibal().username());
        loginPage.password("TEST$test12");
        assertTrue(loginPage.login().isSuccess());
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editPasswordMissingNewPassword() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editPassword(null, "TEST$test12");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        assertEquals("**********", profilePage.getPassword());

        menu.logout();
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editPasswordMissingConfirmPassword() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editPassword("TEST$test12", null);
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        assertEquals("**********", profilePage.getPassword());

        menu.logout();
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editPasswordMissingPasswords() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editPassword(null, null);
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        assertEquals("**********", profilePage.getPassword());

        menu.logout();
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editPasswordMismatch() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editPassword("TEST$test12", "test$TEST12");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        menu.logout();
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
    }

    @Test
    @Scenario(type = ScenarioType.EXCEPTION, failureImpact = FailureImpact.MEDIUM)
    public void editPasswordNotComplicatedEnough() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editPassword("other", "other");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());

        menu.logout();
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
    }
}
