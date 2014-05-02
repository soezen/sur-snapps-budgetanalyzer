package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.validators.UserValidator;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;

import javax.validation.Valid;

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:41
 */
@Controller
public class UserRegistrationController extends AbstractController {

    // TODO selenium tests

    @Autowired
    private UserManager userManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        super.initBinder(binder);
        binder.setValidator(new UserValidator());
    }

    @RequestMapping("/userRegistration")
    public String openUserRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "user_registration";
    }

    @RequestMapping(value = "/postUserRegistration", method = RequestMethod.POST)
    public String userRegistration(@Valid User user, BindingResult bindingResult) {
        validateUserRegistrationInput(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user_registration";
        }
        userManager.createUser(user);
        return "redirect:/budgetanalyzer/user/dashboard";
    }

    private void validateUserRegistrationInput(User user, Errors errors) {
        if (userManager.isUsernameUsed(user.getUsername())) {
            errors.rejectValue("username", "error.user_registration.username_already_used");
        }
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
