package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.pages.user.ManageUsersPage;
import sur.snapps.jetta.database.counter.RecordCounter;
import sur.snapps.jetta.database.counter.table.Table;
import sur.snapps.jetta.database.script.Script;
import sur.snapps.jetta.selenium.annotations.SeleniumTestCase;
import sur.snapps.jetta.selenium.elements.WebPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        homePage.openUserDashboard();
        boolean loginSuccess = loginPage
                .username("hannibal")
                .password("test")
                .login()
                .isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        // TODO add unique constraint on email
        manageUsersPage.revokeInvitation("valid@test.com");

        assertTrue(manageUsersPage.isRevokedTokenPresent("valid@test.com", "04-07-2014"));
        assertFalse(manageUsersPage.isValidTokenPresent("valid@test.com", "04-07-2014"));

        Table tokensTable = new Table("tokens");
        assertEquals(1, counter.count()
                .from(tokensTable)
                .where(and(
                    equal(tokensTable.column("status"), "'REVOKED'"),
                    equal(tokensTable.column("email"), "'valid@test.com'")))
                .get());
        assertEquals(0, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'VALID'"),
                        equal(tokensTable.column("email"), "'valid@test.com'")))
                .get());
    }

    @Test
    public void extendInvitation() {
        homePage.openUserDashboard();
        boolean loginSuccess = loginPage
                .username("hannibal")
                .password("test")
                .login()
                .isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        manageUsersPage.extendInvitation("valid@test.com");

        assertFalse(manageUsersPage.isValidTokenPresent("valid@test.com", "04-07-2014"));
        assertTrue(manageUsersPage.isValidTokenPresent("valid@test.com", "09-07-2014"));

        Table tokensTable = new Table("tokens");
        assertEquals(1, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'VALID'"),
                        equal(tokensTable.column("to_char(expiration_date, 'dd-MM-yyyy')"), "'09-07-2014'"),
                        equal(tokensTable.column("email"), "'valid@test.com'")))
                .get());
        assertEquals(0, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'VALID'"),
                        equal(tokensTable.column("to_char(expiration_date, 'dd-MM-yyyy')"), "'04-07-2014'"),
                        equal(tokensTable.column("email"), "'valid@test.com'")))
                .get());
    }


    @Test
    public void manageUsersAsAdmin() {
        homePage.openUserDashboard();
        boolean loginSuccess = loginPage
                .username("hannibal")
                .password("test")
                .login()
                .isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        assertTrue(manageUsersPage.isUserPresent("John Smith", "hannibal@a-team.com", true));
        assertTrue(manageUsersPage.isUserPresent("Templeton Peck", "face@a-team.com", true));

        assertTrue(manageUsersPage.areTokensVisible());

        assertEquals(3, manageUsersPage.numberOfTokens());
        assertTrue(manageUsersPage.isValidTokenPresent("valid@test.com", "04-07-2014"));
        assertTrue(manageUsersPage.isRevokedTokenPresent("revoke@test.com", "04-07-2014"));
        assertTrue(manageUsersPage.isExpiredTokenPresent("expired@test.com", "03-06-2014"));
    }

    @Test
    public void manageUsersAsNotAdmin() {
        homePage.openUserDashboard();
        boolean loginSuccess = loginPage
                .username("face")
                .password("test")
                .login()
                .isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        assertTrue(manageUsersPage.isUserPresent("John Smith", "hannibal@a-team.com", false));
        assertTrue(manageUsersPage.isUserPresent("Templeton Peck", "face@a-team.com", false));

        assertFalse(manageUsersPage.areTokensVisible());
    }
}
