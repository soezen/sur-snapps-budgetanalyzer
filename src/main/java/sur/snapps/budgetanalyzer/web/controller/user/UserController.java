package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.User;

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:41
 */
@Controller
public class UserController {

    @Autowired
    private UserManager userManager;

    @RequestMapping("/userRegistration")
    public String openUserRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "user_registration";
    }

    @RequestMapping(value = "/postUserRegistration", method = RequestMethod.POST)
    public String userRegistration(User user, BindingResult bindingResult, SessionStatus sessionStatus) {
        validateUserRegistrationInput(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "userRegistration";
        }

        userManager.createUser(user);
        sessionStatus.setComplete();
        return "redirect:/budgetanalyzer/user/dashboard";
    }

    private void validateUserRegistrationInput(User user, Errors errors) {

    }
}
