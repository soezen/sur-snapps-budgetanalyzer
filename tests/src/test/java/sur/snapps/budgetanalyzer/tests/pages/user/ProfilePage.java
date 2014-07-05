package sur.snapps.budgetanalyzer.tests.pages.user;

import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Wait;
import sur.snapps.budgetanalyzer.tests.dummy.DummyToken;
import sur.snapps.budgetanalyzer.tests.dummy.DummyUser;
import sur.snapps.budgetanalyzer.tests.pages.AbstractWebPage;
import sur.snapps.jetta.selenium.elements.Column;
import sur.snapps.jetta.selenium.elements.RowCriteria;
import sur.snapps.jetta.selenium.elements.Table;
import sur.snapps.jetta.selenium.elements.WebTable;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:36
 */
public class ProfilePage extends AbstractWebPage {

    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_STATUS = "status";
    public static final String USERS_COLUMN_ADMIN = "admin";
    public static final String USERS_COLUMN_ACTIONS = "actions";

    public static final String USER_ACTION_REMOVE = "remove_user";

    public static final String TOKENS_COLUMN_EMAIL = "email";
    public static final String TOKENS_COLUMN_EXPIRATION_DATE = "expiration_date";
    public static final String TOKENS_COLUMN_STATUS = "status";
    public static final String TOKENS_COLUMN_ACTIONS = "actions";

    public static final String TOKEN_ACTION_RESEND = "resend_invitation";
    public static final String TOKEN_ACTION_EXTEND = "extend_invitation";
    public static final String TOKEN_ACTION_REVOKE = "revoke_invitation";
    public static final String TOKEN_ACTION_RESTORE = "restore_invitation";

    @FindBy(id = "btn_reload_page")
    private WebElement reloadPageButton;

    // TODO generify accordion
    @FindBy(xpath = "//div[@id='tokens_accordion_panel']")
    private WebElement tokensPanel;
    @FindBy(xpath = "//div[@id='entity_accordion']/div[contains(@class, 'panel')]/div[contains(@class,'panel-heading')]/*[@class='panel-title']/a[@href='#tokens_accordion_panel']")
    private WebElement tokensPanelTitle;

    @WebTable(id = "users", columns = {
            @Column(index = 1, name = USERS_COLUMN_NAME),
            @Column(index = 2, name = USERS_COLUMN_EMAIL),
            @Column(index = 3, name = USERS_COLUMN_STATUS),
            @Column(index = 4, name = USERS_COLUMN_ADMIN),
            @Column(index = 5, name = USERS_COLUMN_ACTIONS, optional = true)
    })
    private Table usersTable;

    @WebTable(id = "tokens", columns = {
            @Column(index = 1, name = TOKENS_COLUMN_EMAIL),
            @Column(index = 2, name = TOKENS_COLUMN_EXPIRATION_DATE),
            @Column(index = 3, name = TOKENS_COLUMN_STATUS),
            @Column(index = 4, name = TOKENS_COLUMN_ACTIONS)
    })
    private Table tokensTable;

    public void reload() {
        reloadPageButton.click();
    }

    public boolean areTokensVisible() {
        return tokensTable.isPresent();
    }

    public int numberOfUsers() {
        return usersTable.numberOfRows();
    }

    public int numberOfTokens() {
        return tokensTable.numberOfRows();
    }

    public Table tokensTable() {
        return tokensTable;
    }

    public Table usersTable() {
        return usersTable;
    }

    public boolean isUserPresent(DummyUser user, boolean hasDisableAction) {
        RowCriteria criteria = maxUserCriteria(user)
                .rowHasNumberOfColumns(hasDisableAction ? 5 : 4);
        if (hasDisableAction) {
            criteria = criteria.columnHasLinks(USERS_COLUMN_ACTIONS, 2);
        }
        return criteria.row() != null;
    }

    private RowCriteria maxUserCriteria(DummyUser user) {
        return minUserCriteria(user.email())
                .columnHasValue(USERS_COLUMN_NAME, user.name())
                .columnHasCheckIcon(USERS_COLUMN_STATUS, user.enabled())
                .columnHasCheckIcon(USERS_COLUMN_ADMIN, user.admin());
    }

    private RowCriteria minUserCriteria(String email) {
        return usersTable.rowCriteria()
                .columnHasLinkWithText(USERS_COLUMN_EMAIL, email);
    }

    private RowCriteria maxTokenCriteria(DummyToken token) {
        return minTokenCriteria(token)
                .columnHasValue(TOKENS_COLUMN_STATUS, token.status())
                .columnHasValue(TOKENS_COLUMN_EXPIRATION_DATE, token.expirationDate())
                .columnHasLinks(TOKENS_COLUMN_ACTIONS, token.nbrOfLinks());
    }

    private RowCriteria minTokenCriteria(DummyToken token) {
        return tokensTable.rowCriteria()
                .columnHasLinkWithText(TOKENS_COLUMN_EMAIL, token.email());
    }

    public boolean isTokenPresent(DummyToken token) {
        return maxTokenCriteria(token)
                .row() != null;
    }

    public void removeUser(String email) {
        minUserCriteria(email)
                .link(USERS_COLUMN_ACTIONS, USER_ACTION_REMOVE)
                .click();
    }

    public void revokeInvitation(DummyToken token) {
        openTokensPanel();
        minTokenCriteria(token)
                .link(TOKENS_COLUMN_ACTIONS, TOKEN_ACTION_REVOKE)
                .click();
    }

    public void extendInvitation(DummyToken token) {
        openTokensPanel();
        minTokenCriteria(token)
                .link(TOKENS_COLUMN_ACTIONS, TOKEN_ACTION_EXTEND)
                .click();
    }

    public void restoreInvitation(DummyToken token) {
        openTokensPanel();
        minTokenCriteria(token)
                .link(TOKENS_COLUMN_ACTIONS, TOKEN_ACTION_RESTORE)
                .click();
    }

    public void resendInvitation(DummyToken token) {
        openTokensPanel();
        minTokenCriteria(token)
                .link(TOKENS_COLUMN_ACTIONS, TOKEN_ACTION_RESEND)
                .click();
    }

    public void assertTokenPanelOpen(Wait<WebDriver> wait, final boolean open) {
        wait.until(new Function<WebDriver, Object>() {
            @Override
            public Object apply(WebDriver input) {
                return open != isTokensPanelCollapsed();
            }
        });
    }

    public boolean isTokensPanelCollapsed() {
        String className = tokensPanel.getAttribute("class");
        System.out.println(className);
        return !className.matches("^(.* )?in( .*)?");
    }

    public void openTokensPanel() {
        if (isTokensPanelCollapsed()) {
            tokensPanelTitle.click();
        }
    }

    public void closeTokensPanel() {
        if (!isTokensPanelCollapsed()) {
            tokensPanelTitle.click();
        }
    }
}