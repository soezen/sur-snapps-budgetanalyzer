package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import sur.snapps.budgetanalyzer.business.event.EventManager;
import sur.snapps.budgetanalyzer.business.user.EditUserView;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

import javax.validation.Valid;
import java.util.List;

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
    private EventManager eventManager;

    @Autowired
    private UserContext userContext;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping("/dashboard")
    public String openUserDashboard(Model model) {
        model.addAttribute("events", eventManager.getEvents(userContext.getCurrentUser().getEntity()));
        return PageLinks.DASHBOARD.page();
    }

    @ModelAttribute("user")
    public User currentUser() {
        return userContext.getCurrentUser();
    }

    @ModelAttribute("users")
    public List<User> usersOfEntity() {
        return userManager.findUsersOfEntity(currentUser().getEntity());
    }

    @ModelAttribute("tokens")
    public List<Token> tokensOfEntity() {
        // TODO only load these if admin user is logged in?
        return tokenManager.findTokensForEntity(currentUser().getEntity());
    }

    @RequestMapping("/profile")
    public String openProfilePage(Model model) {
        User currentUser = userContext.getCurrentUser();
        model.addAttribute("editUser", new EditUserView(currentUser));
        return PageLinks.PROFILE.page();
    }

    @RequestMapping("/disableCurrentUser")
    public String disableCurrentUser() {
        return null;
    }

    @RequestMapping("/postEditUser")
    @NavigateTo(PageLinks.PROFILE)
    public String editCurrentUser(@ModelAttribute("editUser") @Valid EditUserView editUser, BindingResult bindingResult) {
        userValidator.validate(editUser, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BusinessException("form.errors.validation");
        }

        userManager.update(editUser);
        userContext.reset();
        return PageLinks.PROFILE.redirect();
    }
}
