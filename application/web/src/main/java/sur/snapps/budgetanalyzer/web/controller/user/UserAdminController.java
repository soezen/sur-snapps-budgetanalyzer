package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;
import sur.snapps.budgetanalyzer.util.validators.EmailValidator;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;
import sur.snapps.budgetanalyzer.web.response.SuccessResponse;
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
            throw new BusinessException("form.errors.validation");
        }

        User currentUser = userContext.getCurrentUser();
        tokenManager.create(currentUser, user.getEmail(), HttpServletRequestUtil.createUrl(request));

        // TODO show confirmations message
        return PageLinks.PROFILE.redirect();
    }

    private void validateUserInvitation(User user, Errors errors) {
        EmailValidator emailValidator = new EmailValidator();
        if (user.getEmail() == null) {
            errors.rejectValue("email", "error.field.required");
        } else if (!emailValidator.validate(user.getEmail())) {
            errors.rejectValue("email", "error.email.invalid");
        }
    }

    @RequestMapping("/disableUser/{userId}")
    public String disableUser(@PathVariable int userId) {
        // TODO validate that logged in user has access to that user
        // TODO implement removeUser
        // set status to inactive, confirm that user cannot login anymore (should it still be visible in page?)
        // TODO add column status (disabled or enabled)
        // TODO when disabling admin user, all other users will also be disabled
        // TODO refactor manage users page to manage user + entity page
        // present on page: details of entity + details of user
        // as admin following actions: edit entity name, edit user (name, password, email), invitation actions, disable users, disable entity, transfer admin account
        // as normal user following actions: edit user (name, password, email), disable user (only himself)
        // TODO make email address a link
        System.out.println("Removing user: " + userId);
        return PageLinks.PROFILE.redirect();
    }

    @RequestMapping("/invitation_action/{tokenId}")
    @NavigateTo(PageLinks.PROFILE)
    // TODO wrap response in object so we can include error message
    // TODO make javascript show error message
    // TODO make actions visible or invisible with javascript based on other values of the row
    public @ResponseBody
    ResponseHolder<Token> invitationAction(@PathVariable int tokenId, @RequestParam String action, HttpServletRequest request) {
        Token token = null;
        switch (action) {
            case "extend":
                // TODO send email to user informing his invitation was extended? depends on whether we set time invitation valid in user invitation mail
                token = tokenManager.extend(userContext.getCurrentUser(), tokenId);
                break;
            case "resend":
                token = tokenManager.resend(userContext.getCurrentUser(), tokenId, HttpServletRequestUtil.createUrl(request));
                break;
            case "restore":
                token = tokenManager.restore(userContext.getCurrentUser(), tokenId, HttpServletRequestUtil.createUrl(request));
                break;
            case "revoke":
                token = tokenManager.revoke(userContext.getCurrentUser(), tokenId);
                break;
        }
        return new SuccessResponse<Token>(token);
    }
}
