package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.pages.user.ManageUsersPage;
import sur.snapps.jetta.database.script.Script;
import sur.snapps.jetta.selenium.annotations.SeleniumTestCase;
import sur.snapps.jetta.selenium.elements.Table;
import sur.snapps.jetta.selenium.elements.WebPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sur.snapps.budgetanalyzer.tests.pages.user.ManageUsersPage.*;


/**
 * User: SUR
 * Date: 26/05/14
 * Time: 15:35
 */
// TODO load mutliple scripts
@SeleniumTestCase("02 - Manage Users")
@Script("users.sql")
public class ManageUsersTest extends AbstractSeleniumTest {

    @WebPage
    private ManageUsersPage manageUsersPage;

    @Test
    public void testManageUsersContentPresentAsAdmin() {
        homePage.openUserDashboard();
        boolean loginSuccess = loginPage.username("hannibal").password("test").login().isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        Table usersTable = manageUsersPage.usersTable();
        assertEquals("John Smith", usersTable.cellValue(USERS_COLUMN_NAME, 1));
        assertEquals("Mary Bold", usersTable.cellValue(USERS_COLUMN_NAME, 2));
        assertEquals("hannibal@a-team.com", usersTable.cellValue(USERS_COLUMN_EMAIL, 1));
        assertEquals("mary@a-team.com", usersTable.cellValue(USERS_COLUMN_EMAIL, 2));
        assertEquals(1, usersTable.links(USERS_COLUMN_ACTIONS, 1).size());
        assertEquals(2, usersTable.links(USERS_COLUMN_ACTIONS, 2).size());

        assertTrue(manageUsersPage.areTokensVisible());

        assertEquals(3, manageUsersPage.numberOfTokens());
        Table tokensTable = manageUsersPage.tokensTable();
        assertEquals("valid@test.com", tokensTable.cellValue(TOKENS_COLUMN_EMAIL, 1));
        assertEquals("revoke@test.com", tokensTable.cellValue(TOKENS_COLUMN_EMAIL, 2));
        assertEquals("expired@test.com", tokensTable.cellValue(TOKENS_COLUMN_EMAIL, 3));
        assertEquals("26-06-2014", tokensTable.cellValue(TOKENS_COLUMN_EXPIRATION_DATE, 1));
        assertEquals("26-06-2014", tokensTable.cellValue(TOKENS_COLUMN_EXPIRATION_DATE, 2));
        assertEquals("25-05-2014", tokensTable.cellValue(TOKENS_COLUMN_EXPIRATION_DATE, 3));
        assertEquals("VALID", tokensTable.cellValue(TOKENS_COLUMN_STATUS, 1));
        assertEquals("REVOKED", tokensTable.cellValue(TOKENS_COLUMN_STATUS, 2));
        assertEquals("EXPIRED", tokensTable.cellValue(TOKENS_COLUMN_STATUS, 3));
        assertEquals(3, tokensTable.links(TOKENS_COLUMN_ACTIONS, 1).size());
        assertEquals(1, tokensTable.links(TOKENS_COLUMN_ACTIONS, 2).size());
        assertEquals(1, tokensTable.links(TOKENS_COLUMN_ACTIONS, 3).size());
    }

    @Test
    public void testManageUsersContentPresentAsNonAdmin() {
        homePage.openUserDashboard();
        boolean loginSuccess = loginPage.username("mary").password("test").login().isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        Table usersTable = manageUsersPage.usersTable();
        assertEquals("John Smith", usersTable.cellValue(USERS_COLUMN_NAME, 1));
        assertEquals("Mary Bold", usersTable.cellValue(USERS_COLUMN_NAME, 2));
        assertEquals("hannibal@a-team.com", usersTable.cellValue(USERS_COLUMN_EMAIL, 1));
        assertEquals("mary@a-team.com", usersTable.cellValue(USERS_COLUMN_EMAIL, 2));
        assertEquals(0, usersTable.links(USERS_COLUMN_ACTIONS, 1).size());
        assertEquals(0, usersTable.links(USERS_COLUMN_ACTIONS, 2).size());

        assertFalse(manageUsersPage.areTokensVisible());
    }
}
