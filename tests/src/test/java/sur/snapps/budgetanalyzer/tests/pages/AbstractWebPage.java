package sur.snapps.budgetanalyzer.tests.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import sur.snapps.budgetanalyzer.tests.FormResponse;

import java.util.List;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 17:12
 */
public abstract class AbstractWebPage {

    @FindBy(className = "form_error")
    private WebElement formError;

    @FindBy(className = "field_error")
    private List<WebElement> fieldErrors;

    public boolean hasFormError() {
        try {
            return formError.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
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
