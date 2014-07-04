package sur.snapps.budgetanalyzer.tests.pages.user;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import sur.snapps.budgetanalyzer.tests.FormResponse;
import sur.snapps.budgetanalyzer.tests.pages.AbstractWebPage;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 7:36
 */
public class UserRegistrationPage extends AbstractWebPage {

    @FindBy(id = "name")
    private WebElement nameElement;
    @FindBy(id = "username")
    private WebElement usernameElement;
    @FindBy(id = "email")
    private WebElement emailElement;
    @FindBy(id = "newPassword")
    private WebElement newPasswordElement;
    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordElement;

    @FindBy(id = "btn_submit")
    private WebElement registerButton;
    @FindBy(id = "btn_cancel")
    private WebElement cancelButton;

    public UserRegistrationPage name(String name) {
        nameElement.sendKeys(name);
        return this;
    }

    public UserRegistrationPage username(String username) {
        usernameElement.sendKeys(username);
        return this;
    }

    public UserRegistrationPage email(String email) {
        emailElement.sendKeys(email);
        return this;
    }

    public UserRegistrationPage newPassword(String password) {
        newPasswordElement.sendKeys(password);
        return this;
    }

    public UserRegistrationPage confirmPassword(String password) {
        confirmPasswordElement.sendKeys(password);
        return this;
    }

    public FormResponse register() {
        registerButton.click();
        return createFormResponse();
    }

    public void cancel() {
        cancelButton.click();
    }

}
