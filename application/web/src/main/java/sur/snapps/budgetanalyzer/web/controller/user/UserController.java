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
import sur.snapps.budgetanalyzer.business.user.EditEntityView;
import sur.snapps.budgetanalyzer.business.user.EditUserView;
import sur.snapps.budgetanalyzer.business.user.EntityManager;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.exception.ValidationException;
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
    private EntityManager entityManager;


    @Autowired
    private UserValidator userValidator;
    @Autowired
    private EntityValidator entityValidator;

    @RequestMapping("/dashboard")
    public String openUserDashboard(Model model) {
        model.addAttribute("events", eventManager.findFor(userContext.getCurrentUser().entity(), 0, 10));
        return PageLinks.DASHBOARD.page();
    }

    @ModelAttribute("users")
    public List<User> usersOfEntity() {
        return userManager.findUsersOfEntity(currentUser().entity());
    }

    @ModelAttribute("tokens")
    public List<Token> tokensOfEntity() {
        // TODO only load these if admin user is logged in?
        return tokenManager.findTokensForEntity(currentUser().entity());
    }

    @RequestMapping("/profile")
    public String openProfilePage(Model model) {
        User currentUser = userContext.getCurrentUser();
        model.addAttribute("editUser", new EditUserView(currentUser));
        model.addAttribute("editEntity", new EditEntityView(currentUser.entity()));
        return PageLinks.PROFILE.page();
    }

    @RequestMapping("/disableCurrentUser")
    public String disableCurrentUser() {
        return null;
    }

    @RequestMapping("/editentityview/name")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder<Entity> editEntityName(@RequestParam("editentityview-name") String name) {
        EditEntityView editEntity = new EditEntityView(userContext.getCurrentUser().entity());
        editEntity.setName(name);

        return updateEntity(editEntity);
    }

    @RequestMapping("/edituserview/name")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder<User> editCurrentUserName(@RequestParam("edituserview-name") String name) {
        System.out.println("name = " + name);
        EditUserView editUser = new EditUserView(userContext.getCurrentUser());
        editUser.setName(name);

        return updateUser(editUser);
    }

    @RequestMapping("/edituserview/email")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder<User> editCurrentUserEmail(@RequestParam("edituserview-email") String email) {
        EditUserView editUser = new EditUserView(userContext.getCurrentUser());
        editUser.setEmail(email);

        return updateUser(editUser);
    }

    @RequestMapping("/edituserview/confirmPassword")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder<User> editCurrentUserPassword(
            @RequestParam("edituserview-newPassword") String newPassword,
            @RequestParam("edituserview-confirmPassword") String confirmPassword) {
        EditUserView editUser = new EditUserView(userContext.getCurrentUser());
        editUser.setNewPassword(newPassword);
        editUser.setConfirmPassword(confirmPassword);

        BindingResult bindingResult = new BeanPropertyBindingResult(editUser, "editUser");
        if (newPassword == null && confirmPassword == null) {
            bindingResult.rejectValue("newPassword", "error.field.required");
            bindingResult.rejectValue("confirmPassword", "error.field.required");
        }

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }

        return updateUser(editUser);
    }

    private ResponseHolder<User> updateUser(EditUserView editUser) {

        BindingResult bindingResult = new BeanPropertyBindingResult(editUser, "editUser");
        userValidator.validate(editUser, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }

        User updatedUser = userManager.update(editUser);
        userContext.reset();
        return new SuccessResponse<>(updatedUser);
    }

    private ResponseHolder<Entity> updateEntity(EditEntityView editEntity) {
        BindingResult bindingResult = new BeanPropertyBindingResult(editEntity, "editEntity");
        entityValidator.validate(editEntity, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }

        // TODO events overview (choose icons or show text)
        // TODO make invite new user a popup
        Entity updatedEntity = entityManager.update(editEntity);
        userContext.reset();
        return new SuccessResponse<>(updatedEntity);
    }
}
