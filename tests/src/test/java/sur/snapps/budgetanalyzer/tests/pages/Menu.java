package sur.snapps.budgetanalyzer.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:29
 */
public class Menu {

    @FindBy(id = "menu_dashboard")
    private WebElement dashboardLink;
    @FindBy(id = "menu_login")
    private WebElement loginLink;
    @FindBy(id = "menu_register")
    private WebElement registerLink;

    public void register() {
        registerLink.click();
    }

    public void login() {
        loginLink.click();
    }

    public void dashboard() {
        dashboardLink.click();
    }
}
