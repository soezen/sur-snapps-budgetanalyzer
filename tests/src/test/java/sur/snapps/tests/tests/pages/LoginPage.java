package sur.snapps.tests.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import sur.snapps.tests.tests.FormResponse;

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
    @FindBy(id = "btn_login")
    private WebElement loginButton;
    @FindBy(id = "btn_open_registration_page")
    private WebElement openRegistrationPageLink;

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

    public void openRegistrationPage() {
        openRegistrationPageLink.click();
    }
}
