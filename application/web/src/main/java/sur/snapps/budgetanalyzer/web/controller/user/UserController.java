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
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.web.controller.AbstractLoggedInController;
import sur.snapps.budgetanalyzer.web.exception.ValidationException;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;
import sur.snapps.budgetanalyzer.web.util.MessageUtil;

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
public class UserController extends AbstractLoggedInController {

    @Override
    public String activePage() {
        return "user";
    }

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private UserManager userManager;
    @Autowired
    private EventManager eventManager;

    @Autowired
    private UserValidator userValidator;


    @ModelAttribute("users")
    public List<User> usersOfEntity() {
        List<User> usersOfEntity = userManager.findUsersOfEntity(currentUser().entity());
        usersOfEntity.remove(currentUser());
        return usersOfEntity;
    }

    @RequestMapping("/dashboard")
    public String openUserDashboard(Model model) {
        model.addAttribute("events", eventManager.findFor(currentUser().entity(), 0, 10));
        return PageLinks.DASHBOARD.page();
    }

    @RequestMapping("/profile")
    public String openProfilePage(Model model) {
        User currentUser = currentUser();
        model.addAttribute("active_page", "profile");
        model.addAttribute("editUser", new EditUserView(currentUser));
        model.addAttribute("editEntity", new EditEntityView(currentUser.entity()));
        return PageLinks.PROFILE.page();
    }

    // TODO list of users in profile page (mark the person that is admin)
    // TODO table of users in entity page (admin will never be in that list so remove that column)

    @RequestMapping("/disableCurrentUser")
    public String disableCurrentUser() {
        return null;
    }


    @RequestMapping("/update-user-name")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder editCurrentUserName(@RequestParam("edituserview-name") String name) {
        EditUserView editUser = new EditUserView(currentUser());
        editUser.setName(name);

        return updateUser(editUser);
    }

    @RequestMapping("/update-user-email")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder editCurrentUserEmail(@RequestParam("edituserview-email") String email) {
        EditUserView editUser = new EditUserView(currentUser());
        editUser.setEmail(email);

        return updateUser(editUser);
    }

    @RequestMapping("/update-user-password")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody ResponseHolder editCurrentUserPassword(
            @RequestParam("edituserview-newPassword") String newPassword,
            @RequestParam("edituserview-confirmPassword") String confirmPassword) {
        EditUserView editUser = new EditUserView(currentUser());
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

    private ResponseHolder updateUser(EditUserView editUser) {

        BindingResult bindingResult = new BeanPropertyBindingResult(editUser, "editUser");
        userValidator.validate(editUser, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }

        User updatedUser = userManager.update(editUser);
        userContext.reset();
        return ResponseHolder.success(updatedUser, messageUtil.translate("form.profile.update_user.success"));
    }
}
