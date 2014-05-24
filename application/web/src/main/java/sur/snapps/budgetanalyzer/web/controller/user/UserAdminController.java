package sur.snapps.budgetanalyzer.web.controller.user;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.validators.EmailValidator;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;
import sur.snapps.budgetanalyzer.web.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * User: SUR
 * Date: 4/05/14
 * Time: 10:57
 */
@Controller
@RequestMapping("/user/admin")
public class UserAdminController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserContext userContext;


    @RequestMapping("/inviteUser")
    public String openInviteUserPage(Model model) {
        model.addAttribute("user", new User());
        return PageLinks.INVITE_USER.page();
    }

    @RequestMapping(value = "/postInviteUser", method = RequestMethod.POST)
    @NavigateTo(PageLinks.INVITE_USER)
    public String inviteUser(HttpServletRequest request, User user, BindingResult bindingResult) {
        validateUserInvitation(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return PageLinks.INVITE_USER.page();
        }

        User currentUser = userContext.getCurrentUser();
        tokenManager.create(currentUser.getEntity(), user.getEmail(), currentUser.getName(), HttpServletRequestUtil.createUrl(request));

        // TODO show confirmations message
        return PageLinks.MANAGE_USERS.redirect();
    }

    private void validateUserInvitation(User user, Errors errors) {
        EmailValidator emailValidator = new EmailValidator();
        if (Strings.isNullOrEmpty(user.getEmail())) {
            errors.rejectValue("email", "error.field.required");
        } else if (!emailValidator.validate(user.getEmail())) {
            errors.rejectValue("email", "error.email.invalid");
        }
    }

    @RequestMapping("/removeUser/{userId}")
    public String removeUser(@PathVariable int userId) {
        // TODO do not remove admin user
        // TODO validate that logged in user has access to that user
        // TODO implement removeUser
        // set status to inactive, confirm that user cannot login anymore (should it still be visible in page?)
        System.out.println("Removing user: " + userId);
        return PageLinks.MANAGE_USERS.redirect();
    }

    @RequestMapping("/restoreInvitation/{tokenId}")
    public String restoreInvitation(@PathVariable int tokenId, HttpServletRequest request) {
        tokenManager.restore(userContext.getCurrentUser().getId(), tokenId, HttpServletRequestUtil.createUrl(request));
        return PageLinks.MANAGE_USERS.redirect();
    }

    @RequestMapping("/extendInvitation/{tokenId}")
    @NavigateTo(PageLinks.MANAGE_USERS)
    public String extendInvitation(@PathVariable int tokenId) {
        // TODO send email to user informing his invitation was extended? depends on whether we set time invitation valid in user invitation mail
        tokenManager.extend(userContext.getCurrentUser().getId(), tokenId);
        return PageLinks.MANAGE_USERS.redirect();
    }

    @RequestMapping("/resendInvitation/{tokenId}")
    public String resendInvitation(@PathVariable int tokenId, HttpServletRequest request) {
        tokenManager.resend(userContext.getCurrentUser().getId(), tokenId, HttpServletRequestUtil.createUrl(request));
        return PageLinks.MANAGE_USERS.redirect();
    }

    @RequestMapping("/revokeInvitation/{tokenId}")
    public String revokeInvitation(@PathVariable int tokenId) {
        tokenManager.revoke(userContext.getCurrentUser().getId(), tokenId);
        return PageLinks.MANAGE_USERS.redirect();
    }
}
