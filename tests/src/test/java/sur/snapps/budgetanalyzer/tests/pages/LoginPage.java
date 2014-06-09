package sur.snapps.budgetanalyzer.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import sur.snapps.budgetanalyzer.tests.FormResponse;
import sur.snapps.budgetanalyzer.tests.dummy.DummyUser;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:30
 */
public class LoginPage extends AbstractWebPage {

    // TODO add cancel button
    @FindBy(id = "username")
    private WebElement usernameElement;
    @FindBy(id = "password")
    private WebElement passwordElement;
    @FindBy(id = "btn_submit")
    private WebElement loginButton;

    public LoginPage username(String username) {
        usernameElement.sendKeys(username);
        return this;
    }

    public LoginPage password(String password) {
        passwordElement.sendKeys(password);
        return this;
    }

    public FormResponse login() {
        loginButton.click();
        return createFormResponse();
    }

    public FormResponse login(DummyUser user) {
        username(user.username());
        password(user.password());
        loginButton.click();
        return createFormResponse();
    }
}
