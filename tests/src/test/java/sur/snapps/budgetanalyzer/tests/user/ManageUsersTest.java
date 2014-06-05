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
import static sur.snapps.budgetanalyzer.tests.dummy.Tokens.expired;
import static sur.snapps.budgetanalyzer.tests.dummy.Tokens.revoked;
import static sur.snapps.budgetanalyzer.tests.dummy.Tokens.valid;
import static sur.snapps.budgetanalyzer.tests.dummy.Tokens.validExtended;
import static sur.snapps.budgetanalyzer.tests.dummy.Tokens.validRevoked;
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
        homePage.openUserDashboard();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        dashboardPage.manageUsers();

        // TODO add unique constraint on email

        manageUsersPage.revokeInvitation(valid());

        assertFalse(manageUsersPage.isTokenPresent(valid()));
        assertTrue(manageUsersPage.isTokenPresent(validRevoked()));

        Table tokensTable = new Table("tokens");
        assertEquals(1, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'" + validRevoked().status() + "'"),
                        equal(tokensTable.column("email"), "'" + validRevoked().email() + "'")))
                .get());
        assertEquals(0, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'" + valid().status() + "'"),
                        equal(tokensTable.column("email"), "'" + valid().email() + "'")))
                .get());
    }

    @Test
    public void extendInvitation() {
        homePage.openUserDashboard();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        dashboardPage.manageUsers();

        manageUsersPage.extendInvitation(valid());

        assertFalse(manageUsersPage.isTokenPresent(valid()));
        assertTrue(manageUsersPage.isTokenPresent(validExtended()));

        Table tokensTable = new Table("tokens");
        assertEquals(1, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'" + validExtended().status() + "'"),
                        equal(tokensTable.column("to_char(expiration_date, 'dd-MM-yyyy')"), "'" + validExtended().expirationDate() + "'"),
                        equal(tokensTable.column("email"), "'" + validExtended().email() + "'")))
                .get());
        assertEquals(0, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'" + valid().status() + "'"),
                        equal(tokensTable.column("to_char(expiration_date, 'dd-MM-yyyy')"), "'" + valid().expirationDate() + "'"),
                        equal(tokensTable.column("email"), "'" + valid().email() + "'")))
                .get());
    }


    @Test
    public void manageUsersAsAdmin() {
        homePage.openUserDashboard();
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
        homePage.openUserDashboard();
        assertTrue(loginPage.login(face()).isSuccess());
        dashboardPage.manageUsers();

        assertEquals(2, manageUsersPage.numberOfUsers());
        assertTrue(manageUsersPage.isUserPresent(hannibal(), false));
        assertTrue(manageUsersPage.isUserPresent(face(), false));

        assertFalse(manageUsersPage.areTokensVisible());
    }
}
