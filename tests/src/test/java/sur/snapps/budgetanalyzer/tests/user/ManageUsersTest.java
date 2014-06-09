package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.dummy.DummyToken;
import sur.snapps.budgetanalyzer.tests.pages.user.ManageUsersPage;
import sur.snapps.jetta.database.counter.RecordCounter;
import sur.snapps.jetta.database.counter.table.Table;
import sur.snapps.jetta.database.script.Script;
import sur.snapps.jetta.selenium.annotations.SeleniumTestCase;
import sur.snapps.jetta.selenium.elements.WebPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sur.snapps.budgetanalyzer.tests.dummy.Tokens.*;
import static sur.snapps.budgetanalyzer.tests.dummy.Users.face;
import static sur.snapps.budgetanalyzer.tests.dummy.Users.hannibal;
import static sur.snapps.jetta.database.counter.expression.conditional.Conditionals.equal;
import static sur.snapps.jetta.database.counter.expression.operator.Operators.and;


/**
 * User: SUR
 * Date: 26/05/14
 * Time: 15:35
 */
// TODO load mutliple scripts
@SeleniumTestCase("02 - Manage Users")
@Script("users.sql")
public class ManageUsersTest extends AbstractSeleniumTest {

    private RecordCounter counter;

    @WebPage
    private ManageUsersPage manageUsersPage;

    @Test
    public void revokeInvitation() {
        menu.dashboard();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        dashboardPage.manageUsers();

        // TODO add unique constraint on email

        manageUsersPage.revokeInvitation(valid());

        assertTrue(manageUsersPage.isTokenPresent(validRevoked()));
        assertFalse(manageUsersPage.isTokenPresent(valid()));

        assertTokenPresentInDatabase(validRevoked(), true);
        assertTokenPresentInDatabase(valid(), false);
    }

    @Test
    public void extendInvitation() {
        menu.dashboard();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        dashboardPage.manageUsers();

        manageUsersPage.extendInvitation(valid());

        assertTrue(manageUsersPage.isTokenPresent(validExtended()));
        assertFalse(manageUsersPage.isTokenPresent(valid()));

        assertTokenPresentInDatabase(validExtended(), true);
        assertTokenPresentInDatabase(valid(), false);
    }

    @Test
    public void restoreRevokedInvitation() {
        menu.dashboard();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        dashboardPage.manageUsers();

        manageUsersPage.restoreInvitation(revoked());

        assertTrue(manageUsersPage.isTokenPresent(revokedRestored()));
        assertFalse(manageUsersPage.isTokenPresent(revoked()));

        assertTokenPresentInDatabase(revokedRestored(), true);
        assertTokenPresentInDatabase(revoked(), false);
    }

    @Test
    public void restoreExpiredInvitation() {
        menu.dashboard();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        dashboardPage.manageUsers();

        manageUsersPage.restoreInvitation(expired());

        assertTrue(manageUsersPage.isTokenPresent(expiredRestored()));
        assertFalse(manageUsersPage.isTokenPresent(expired()));

        assertTokenPresentInDatabase(expiredRestored(), true);
        assertTokenPresentInDatabase(expired(), false);
    }

    // TODO try to get the assertion error messages to be more specific

    @Test
    public void manageUsersAsAdmin() {
        menu.dashboard();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        assertTrue(manageUsersPage.isUserPresent(hannibal(), true));
        assertTrue(manageUsersPage.isUserPresent(face(), true));

        assertTrue(manageUsersPage.areTokensVisible());

        assertEquals(3, manageUsersPage.numberOfTokens());
        assertTrue(manageUsersPage.isTokenPresent(valid()));
        assertTrue(manageUsersPage.isTokenPresent(revoked()));
        assertTrue(manageUsersPage.isTokenPresent(expired()));
    }

    @Test
    public void manageUsersAsNotAdmin() {
        menu.dashboard();
        assertTrue(loginPage.login(face()).isSuccess());
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        assertTrue(manageUsersPage.isUserPresent(hannibal(), false));
        assertTrue(manageUsersPage.isUserPresent(face(), true));

        assertFalse(manageUsersPage.areTokensVisible());
    }

    private void assertTokenPresentInDatabase(DummyToken token, boolean present) {
        Table tokensTable = new Table("tokens");
        assertEquals(present ? 1 : 0, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'" + token.status() + "'"),
                        equal(tokensTable.column("to_char(expiration_date, 'dd-MM-yyyy')"), "'" + token.expirationDate() + "'"),
                        equal(tokensTable.column("email"), "'" + token.email() + "'")))
                .get());
    }
}
