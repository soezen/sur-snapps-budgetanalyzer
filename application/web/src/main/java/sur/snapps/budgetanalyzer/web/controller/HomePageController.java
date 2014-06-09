package sur.snapps.budgetanalyzer.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

/**
 * User: SUR
 * Date: 9/06/14
 * Time: 14:07
 */
@Controller
public class HomePageController extends AbstractController {

    @RequestMapping("/homepage")
    public String openHomePage() {
        return PageLinks.HOMEPAGE.page();
    }

    @RequestMapping("/login")
    public String openLoginPage() {
        return PageLinks.LOGIN.page();
    }

    @RequestMapping("/login/error")
    public String openLoginErrorPage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("error_message", "form.login.invalid_credentials");
        return PageLinks.LOGIN.redirect();
    }

    @RequestMapping("/denied")
    public String openDeniedPage() {
        return PageLinks.DENIED.page();
    }
}
