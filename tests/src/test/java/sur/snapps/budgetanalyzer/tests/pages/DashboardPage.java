package sur.snapps.budgetanalyzer.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:29
 */
public class DashboardPage {

    @FindBy(id = "btn_manage_users")
    private WebElement manageUsersLink;

    public void manageUsers() {
        manageUsersLink.click();
    }
}
