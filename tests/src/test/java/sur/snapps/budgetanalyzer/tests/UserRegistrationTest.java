package sur.snapps.budgetanalyzer.tests;

import org.junit.Test;
import org.openqa.selenium.By;

public class UserRegistrationTest extends AbstractSeleniumTest {


    @Test
    public void userRegistration() throws Exception {
        // TODO refactor
        driver.findElement(By.linkText("User Dashboard")).click();
    }

    @Override
    protected void goToStartLocation() {
        driver.get("http://budgetanalyzer.snapps.cloudbees.net/");
    }

    @Override
    protected UseCase getUseCase() {
        return UseCase.USER_REGISTRATION;
    }
}
