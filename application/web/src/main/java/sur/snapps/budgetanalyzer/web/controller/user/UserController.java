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
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.mail.Url;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.validators.EmailValidator;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Preconditions.checkNotNull;

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
    private UserManager userManager;
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private LoginContext loginContext;

    @RequestMapping("/dashboard")
    public String openUserDashboard() {
        return PageLinks.DASHBOARD.page();
    }

    @RequestMapping("/inviteUser")
    public String openInviteUserPage(Model model) {
        model.addAttribute("user", new User());
        return PageLinks.INVITE_USER.page();
    }

    @RequestMapping("/manageUsers")
    public String openManageUsersPage(Model model) {
        Entity entity = loginContext.getCurrentUser().getEntity();
//        model.addAttribute("users", userManager.findUsersOfEntity(entity));
        model.addAttribute("tokens", tokenManager.findTokensForEntity(entity));
        return PageLinks.MANAGE_USERS.page();
    }

    @RequestMapping(value = "/postInviteUser", method = RequestMethod.POST)
    @NavigateTo(PageLinks.INVITE_USER)
    public String inviteUser(HttpServletRequest request, User user, BindingResult bindingResult) {
        validateUserInvitation(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return PageLinks.INVITE_USER.error();
        }
        // TODO add name to user (and use that everywhere to display (mail and webapp))
        Url url = new Url();
        url.setServerName(request.getServerName());
        url.setServerPort(request.getServerPort());
        url.setContextPath(request.getContextPath());
        checkNotNull(tokenManager);
        checkNotNull(loginContext);
        tokenManager.createToken(loginContext.getCurrentUser().getEntity(), user.getEmail(), user.getUsername(), url);

        // TODO show confirmations message
        return PageLinks.INVITE_USER.confirmation();
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
