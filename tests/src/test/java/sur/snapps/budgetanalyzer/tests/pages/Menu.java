package sur.snapps.budgetanalyzer.tests.pages;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import sur.snapps.jetta.selenium.annotations.WaitElement;
import sur.snapps.jetta.selenium.elements.BaseUrl;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:29
 */
public class Menu {


    @WaitElement
    private FluentWait<WebDriver> wait;

    @FindBy(id = "menu_dashboard")
    private WebElement dashboardLink;
    @FindBy(id = "menu_login")
    private WebElement loginLink;
    @FindBy(id = "menu_logout")
    private WebElement logoutLink;
    @FindBy(id = "menu_register")
    private WebElement registerLink;
    @FindBy(id = "menu_profile")
    private WebElement profileLink;

    // TODO only load what you see on page

    @BaseUrl
    private String baseUrl;

    public void profile() {
        profileLink.click();
    }

    public void register() {
        // TODO get firefox working or find out why ie does not click on buttons or links
        registerLink.click();
        wait.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return driver.getCurrentUrl().startsWith(baseUrl + "/budgetanalyzer/userRegistration");
            }
        });
    }

    public void login() {
        loginLink.click();
        wait.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return driver.getCurrentUrl().startsWith(baseUrl + "/budgetanalyzer/login");
            }
        });
    }

    public void logout() {
        logoutLink.click();
        wait.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return input.getCurrentUrl().startsWith(baseUrl + "/budgetanalyzer/homepage");
            }
        });
    }

    public void dashboard() {
        dashboardLink.click();
    }
}
