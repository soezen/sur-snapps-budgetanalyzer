package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.validators.UserValidator;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

import javax.validation.Valid;

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:41
 */
@Controller
//@Session
public class UserRegistrationController extends AbstractController {

    // TODO selenium tests
    @Autowired
    private UserContext userContext;

    @Autowired
    private UserManager userManager;
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping("/userRegistration")
    public String openUserRegistrationPage(Model model) {
        User user = new User();
        Entity entity = new Entity();
        entity.setOwned(true);
        entity.setShared(false);
        user.setEntity(entity);
        user.setAdmin(true);
        model.addAttribute("user", user);
        return PageLinks.USER_REGISTRATION.page();
    }

    @RequestMapping(value = "/userRegistrationWithToken")
    public String openUserRegistrationWithTokenPage(Model model, @RequestParam String value) {
        Token token = tokenManager.findTokenByValue(value);
        // TODO check if token valid

        User user = new User();
        // TODO make read only
        user.setEmail(token.getEmail());
        user.setTokenValue(value);

        model.addAttribute("user", user);
        return PageLinks.USER_REGISTRATION.page();
    }

    @RequestMapping(value = "/postUserRegistration", method = RequestMethod.POST)
    @NavigateTo(PageLinks.USER_REGISTRATION)
    public String userRegistration(@Valid User user, BindingResult bindingResult) {
        validateUserRegistrationInput(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return PageLinks.USER_REGISTRATION.error();
        }
        userManager.createUser(user);
        return PageLinks.USER_REGISTRATION.confirmation();
    }

    private void validateUserRegistrationInput(User user, Errors errors) {
        userValidator.validate(user, errors);

        // TODO add validation: if !admin -> tokenvalue required

        if (userManager.isUsernameUsed(user.getUsername())) {
            errors.rejectValue("username", "error.user_registration.username_already_used");
        }
    }
}
