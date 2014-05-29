package sur.snapps.budgetanalyzer.tests.pages.user;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import sur.snapps.budgetanalyzer.tests.pages.AbstractWebPage;
import sur.snapps.unitils.modules.selenium.Table;
import sur.snapps.unitils.modules.selenium.page.elements.Column;
import sur.snapps.unitils.modules.selenium.page.elements.WebTable;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:36
 */
public class ManageUsersPage extends AbstractWebPage {

    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_ACTIONS = "actions";
    public static final String TOKENS_COLUMN_EMAIL = "email";
    public static final String TOKENS_COLUMN_EXPIRATION_DATE = "expiration_date";
    public static final String TOKENS_COLUMN_STATUS = "status";
    public static final String TOKENS_COLUMN_ACTIONS = "actions";

    @FindBy(id = "btn_reload_page")
    private WebElement reloadPageButton;

    @WebTable(id = "users", columns = {
            @Column(index = 0, name = USERS_COLUMN_NAME),
            @Column(index = 1, name = USERS_COLUMN_EMAIL),
            @Column(index = 2, name = USERS_COLUMN_ACTIONS, optional = true)
    })
    private Table usersTable;
    @WebTable(id = "tokens", columns = {
            @Column(index = 0, name = TOKENS_COLUMN_EMAIL),
            @Column(index = 1, name = TOKENS_COLUMN_EXPIRATION_DATE),
            @Column(index = 2, name = TOKENS_COLUMN_STATUS),
            @Column(index = 3, name = TOKENS_COLUMN_ACTIONS)
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
}
