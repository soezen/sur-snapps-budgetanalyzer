package sur.snapps.budgetanalyzer.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:29
 */
public class HomePage {

    @FindBy(id = "btn_open_user_dashboard")
    private WebElement userDashboardLink;

    public void openUserDashboard() {
        userDashboardLink.click();
    }
}
