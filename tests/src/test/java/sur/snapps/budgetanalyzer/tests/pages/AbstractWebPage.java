package sur.snapps.budgetanalyzer.tests.pages;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import sur.snapps.budgetanalyzer.tests.FormResponse;
import sur.snapps.jetta.selenium.annotations.WaitElement;

import java.util.List;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 17:12
 */
public abstract class AbstractWebPage {

    @WaitElement
    protected FluentWait<WebDriver> wait;

    @FindBy(css = "#form_response.alert-danger")
    private WebElement formError;
    @FindBy(css = "#form_response.alert-success")
    private WebElement formSuccess;

    @FindBy(css = "div.form-group.has-error p.text-danger > span[id$='.errors']")
    private List<WebElement> fieldErrors;

    public boolean hasFormError() {
        try {
            return formError.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isActionSuccess() {
        try {
            return formSuccess.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForFormResponse() {
        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return input.findElement(By.id("form_response")).isDisplayed();
            }
        });
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    protected FormResponse createFormResponse() {
        FormResponse formResponse = FormResponse.newResponse().formError(hasFormError());
        for (WebElement fieldError : fieldErrors) {
            formResponse.fieldError(fieldError.getAttribute("id"));
        }
        return formResponse;
    }
}
