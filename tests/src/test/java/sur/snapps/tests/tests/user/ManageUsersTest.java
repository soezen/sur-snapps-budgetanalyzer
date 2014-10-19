package sur.snapps.tests.tests.user;

import org.junit.Test;
import sur.snapps.tests.tests.AbstractSeleniumTest;
import sur.snapps.tests.tests.pages.user.ManageUsersPage;
import sur.snapps.unitils.modules.database.Script;
import sur.snapps.unitils.modules.selenium.SeleniumTestCase;
import sur.snapps.unitils.modules.selenium.Table;
import sur.snapps.unitils.modules.selenium.page.elements.WebPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sur.snapps.tests.tests.pages.user.ManageUsersPage.*;


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
        boolean loginSuccess = loginPage.username("sur").password("test").login().isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        Table usersTable = manageUsersPage.usersTable();
        assertEquals("Rogge Suzan", usersTable.cellValue(USERS_COLUMN_NAME, 1));
        assertEquals("Gert Raeyen", usersTable.cellValue(USERS_COLUMN_NAME, 2));
        assertEquals("rogge.suzan@gmail.com", usersTable.cellValue(USERS_COLUMN_EMAIL, 1));
        assertEquals("gert@raeyen.be", usersTable.cellValue(USERS_COLUMN_EMAIL, 2));
        assertEquals(1, usersTable.links(USERS_COLUMN_ACTIONS, 1).size());
        assertEquals(1, usersTable.links(USERS_COLUMN_ACTIONS, 2).size());

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
        boolean loginSuccess = loginPage.username("ger").password("test").login().isSuccess();
        assertTrue(loginSuccess);
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        Table usersTable = manageUsersPage.usersTable();
        assertEquals("Rogge Suzan", usersTable.cellValue(USERS_COLUMN_NAME, 1));
        assertEquals("Gert Raeyen", usersTable.cellValue(USERS_COLUMN_NAME, 2));
        assertEquals("rogge.suzan@gmail.com", usersTable.cellValue(USERS_COLUMN_EMAIL, 1));
        assertEquals("gert@raeyen.be", usersTable.cellValue(USERS_COLUMN_EMAIL, 2));
        assertEquals(0, usersTable.links(USERS_COLUMN_ACTIONS, 1).size());
        assertEquals(0, usersTable.links(USERS_COLUMN_ACTIONS, 2).size());

        assertFalse(manageUsersPage.areTokensVisible());
    }
}
