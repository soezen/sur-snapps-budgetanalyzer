package sur.snapps.budgetanalyzer.web.controller.user;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.domain.util.validators.EmailValidator;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;

/**
 * UserDashboardController
 *
 * User: SUR
 * Date: 22/04/14
 * Time: 19:43
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private LoginContext loginContext;

    @RequestMapping("/dashboard")
    public String openUserDashboard() {
        return "user/dashboard";
    }

    @RequestMapping("/inviteUser")
    public String openInviteUserPage(Model model) {
        model.addAttribute("user", new User());
        return "user/user_invitation";
    }

    @RequestMapping(value = "/postInviteUser", method = RequestMethod.POST)
    public String inviteUser(User user, BindingResult bindingResult) {
        validateUserInvitation(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/user_invitation";
        }
        // TODO add name to user (and use that everywhere to display (mail and webapp))
        tokenManager.createToken(loginContext.getCurrentUser().getEntity(), user.getEmail(), user.getUsername());
        // TODO show confirmations message
        return "redirect:/budgetanalyzer/user/dashboard";
    }

    private void validateUserInvitation(User user, Errors errors) {
        EmailValidator emailValidator = new EmailValidator();
        if (Strings.isNullOrEmpty(user.getEmail())) {
            errors.rejectValue("email", "error.field.required");
        } else if (!emailValidator.validate(user.getEmail())) {
            errors.rejectValue("email", "error.email.invalid");
        }
    }
}
