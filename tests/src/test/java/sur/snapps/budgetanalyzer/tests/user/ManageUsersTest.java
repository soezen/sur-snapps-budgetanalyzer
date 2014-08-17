package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.dummy.DummyToken;
import sur.snapps.budgetanalyzer.tests.pages.user.ProfilePage;
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
import static sur.snapps.jetta.database.counter.expression.conditional.Conditionals.equalDate;
import static sur.snapps.jetta.database.counter.expression.operation.Operations.and;


/**
 * User: SUR
 * Date: 26/05/14
 * Time: 15:35
 */
// TODO load mutliple scripts
@SeleniumTestCase("03 - Manage Users")
@Script("users.sql")
public class ManageUsersTest extends AbstractSeleniumTest {

    private RecordCounter counter;

    @WebPage
    private ProfilePage profilePage;

    @Test
    public void revokeInvitation() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        // TODO add unique constraint on email

        profilePage.revokeInvitation(valid());

        profilePage.assertTokenPanelOpen(wait, true);
        assertTrue(profilePage.isTokenPresent(validRevoked()));
        assertFalse(profilePage.isTokenPresent(valid()));

        assertTokenPresentInDatabase(validRevoked(), true);
        assertTokenPresentInDatabase(valid(), false);
    }

    @Test
    public void extendInvitation() throws InterruptedException {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.extendInvitation(valid());

        profilePage.assertTokenPanelOpen(wait, true);
        assertTrue(profilePage.isTokenPresent(validExtended()));
        assertFalse(profilePage.isTokenPresent(valid()));

        assertTokenPresentInDatabase(validExtended(), true);
        assertTokenPresentInDatabase(valid(), false);
    }

    @Test
    public void restoreRevokedInvitation() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.restoreInvitation(revoked());

        profilePage.assertTokenPanelOpen(wait, true);
        assertTrue(profilePage.isTokenPresent(revokedRestored()));
        assertFalse(profilePage.isTokenPresent(revoked()));

        assertTokenPresentInDatabase(revokedRestored(), true);
        assertTokenPresentInDatabase(revoked(), false);
    }

    @Test
    public void restoreExpiredInvitation() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.restoreInvitation(expired());

        profilePage.assertTokenPanelOpen(wait, true);
        assertTrue(profilePage.isTokenPresent(expiredRestored()));
        assertFalse(profilePage.isTokenPresent(expired()));

        // TODO make this method available everywhere
        assertTokenPresentInDatabase(expiredRestored(), true);
        assertTokenPresentInDatabase(expired(), false);
    }

    // TODO try to get the assertion error messages to be more specific

    @Test
    public void manageUsersAsAdmin() {
        // TODO add assertions for user info
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();
        // TODO-TECH remove deploy from jenkins build and make a separate one for that

        assertEquals(1, profilePage.numberOfUsers());
        assertTrue(profilePage.isUserPresent(face(), true));

        assertTrue(profilePage.areTokensVisible());

        assertEquals(3, profilePage.numberOfTokens());
        assertTrue(profilePage.isTokenPresent(valid()));
        assertTrue(profilePage.isTokenPresent(revoked()));
        assertTrue(profilePage.isTokenPresent(expired()));
    }

    @Test
    public void manageUsersAsNotAdmin() {
        // TODO add assertions for user info
        menu.login();
        assertTrue(loginPage.login(face()).isSuccess());
        menu.profile();

        assertEquals(1, profilePage.numberOfUsers());
        assertTrue(profilePage.isUserPresent(hannibal(), false));

        assertFalse(profilePage.areTokensVisible());
    }

    private void assertTokenPresentInDatabase(DummyToken token, boolean present) {
        Table tokensTable = new Table("tokens");
        assertEquals(present ? 1 : 0, counter.count()
                .from(tokensTable)
                .where(and(
                        equal(tokensTable.column("status"), "'" + token.status() + "'"),
                        equalDate(tokensTable.column("expiration_date"), token.getExpirationDate()),
                        equal(tokensTable.column("email"), "'" + token.email() + "'")))
                .get());
    }
}
