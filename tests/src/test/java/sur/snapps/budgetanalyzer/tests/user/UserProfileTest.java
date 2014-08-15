package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.pages.user.ProfilePage;
import sur.snapps.jetta.database.counter.RecordCounter;
import sur.snapps.jetta.database.counter.table.Table;
import sur.snapps.jetta.database.script.Script;
import sur.snapps.jetta.selenium.annotations.SeleniumTestCase;
import sur.snapps.jetta.selenium.elements.WebPage;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sur.snapps.budgetanalyzer.tests.dummy.Users.hannibal;
import static sur.snapps.jetta.database.counter.expression.conditional.Conditionals.equal;
import static sur.snapps.jetta.database.counter.expression.operator.Operators.and;

/**
 * User: SUR
 * Date: 5/07/14
 * Time: 15:26
 */
@SeleniumTestCase("02 - User Profile")
@Script("users.sql")
public class UserProfileTest extends AbstractSeleniumTest {

    private RecordCounter counter;

    @WebPage
    private ProfilePage profilePage;

    @Test
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

    // TODO test cancel button

    @Test
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


    // TODO test password success
    // TODO test password one field missing
    // TODO test other password field missing
    // TODO test both password fields missing
    // TODO test password mismatch
    // TODO test password not sufficiently complicated
}
