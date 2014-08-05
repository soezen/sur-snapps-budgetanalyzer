package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;
import sur.snapps.budgetanalyzer.web.response.SuccessResponse;

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

    @RequestMapping(value = "/editUser/name")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder<User> editCurrentUserName(@RequestParam String name) {

        EditUserView editUser = new EditUserView(userContext.getCurrentUser());
        editUser.setName(name);

        return updateUser(editUser);
    }

    @RequestMapping("/editUser/email")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder<User> editCurrentUserEmail(@RequestParam String email) {
        EditUserView editUser = new EditUserView(userContext.getCurrentUser());
        editUser.setEmail(email);

        return updateUser(editUser);
    }

    @RequestMapping("/editUser/confirmPassword")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder<User> editCurrentUserPassword(@RequestParam String newPassword, @RequestParam String confirmPassword) {
        EditUserView editUser = new EditUserView(userContext.getCurrentUser());
        editUser.setNewPassword(newPassword);
        editUser.setConfirmPassword(confirmPassword);

        return updateUser(editUser);
    }

    private ResponseHolder<User> updateUser(EditUserView editUser) {

        BindingResult bindingResult = new BeanPropertyBindingResult(editUser, "editUser");
        userValidator.validate(editUser, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BusinessException("form.errors.validation");
        }

        User updatedUser = userManager.update(editUser);
        userContext.reset();
        return new SuccessResponse<>(updatedUser);
    }
}
