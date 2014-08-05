package sur.snapps.budgetanalyzer.tests.user;

import org.junit.Test;
import sur.snapps.budgetanalyzer.tests.AbstractSeleniumTest;
import sur.snapps.budgetanalyzer.tests.pages.user.ProfilePage;
import sur.snapps.jetta.database.counter.RecordCounter;
import sur.snapps.jetta.database.script.Script;
import sur.snapps.jetta.selenium.annotations.SeleniumTestCase;
import sur.snapps.jetta.selenium.elements.WebPage;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sur.snapps.budgetanalyzer.tests.dummy.Users.hannibal;

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

        // TODO DB assertions
    }

    @Test
    public void editNameEmpty() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editName("");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());
        assertTrue(profilePage.hasFieldErrors());

        // TODO-BUG cancel does not work on fields with field error
        // TODO-BUG when showing fields with validation error, show everything from that group

        // option: make submit an ajax call which return success or error message
        // do not show edit input when error but only error message
        // -> solves 3 issues: layout problem with X and cancel that will work again and issue with multiple inputs in one group
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
    }

    @Test
    public void editEmailEmpty() {
        menu.login();
        assertTrue(loginPage.login(hannibal()).isSuccess());
        menu.profile();

        profilePage.editEmail("");
        assertFalse(profilePage.isActionSuccess());
        assertTrue(profilePage.hasFormError());
        assertTrue(profilePage.hasFieldErrors());

        // TODO test cancel button
    }
}
