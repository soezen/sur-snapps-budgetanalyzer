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
import sur.snapps.budgetanalyzer.business.exception.BusinessException;
import sur.snapps.budgetanalyzer.business.user.EditEntityView;
import sur.snapps.budgetanalyzer.business.user.EditUserView;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.validators.EmailValidator;
import sur.snapps.budgetanalyzer.web.controller.AbstractLoggedInController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;
import sur.snapps.budgetanalyzer.web.util.HttpServletRequestUtil;
import sur.snapps.budgetanalyzer.web.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * User: SUR
 * Date: 4/05/14
 * Time: 10:57
 */
@Controller
@RequestMapping("/user/admin")
public class UserAdminController extends AbstractLoggedInController {

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public String activePage() {
        return "entity_settings";
    }

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserManager userManager;

    @RequestMapping("/entitySettings")
    public String openEntitySettingsPage(Model model) {
        Entity entity = currentUser().entity();
        List<Token> tokens = tokenManager.findTokensForEntity(entity);
        model.addAttribute("tokens", tokens);
        model.addAttribute("editEntity", new EditEntityView(entity));
        return PageLinks.ENTITY_SETTINGS.page();
    }

    @RequestMapping("/inviteUser")
    public String openInviteUserPage(Model model) {
        model.addAttribute("user", new EditUserView());
        return PageLinks.INVITE_USER.page();
    }

    @RequestMapping(value = "/postInviteUser", method = RequestMethod.POST)
    @NavigateTo(PageLinks.INVITE_USER)
    public String inviteUser(HttpServletRequest request, EditUserView user, BindingResult bindingResult) {
        validateUserInvitation(user, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BusinessException("form.errors.validation");
        }

        tokenManager.create(currentUser(), user.getEmail(), HttpServletRequestUtil.createUrl(request));

        return PageLinks.ENTITY_SETTINGS.redirect();
    }

    private void validateUserInvitation(EditUserView user, Errors errors) {
        EmailValidator emailValidator = new EmailValidator();
        if (isNullOrEmpty(user.getEmail())) {
            errors.rejectValue("email", "error.field.required");
        } else if (!emailValidator.validate(user.getEmail())) {
            errors.rejectValue("email", "error.email.invalid");
        }
    }

    @RequestMapping("/disableUser/{userId}")
    public String disableUser(@PathVariable int userId) {
        // TODO-FUNC UC-1 validate that logged in user has access to that user
        // TODO-FUNC UC-1 implement removeUser
        // set status to inactive, confirm that user cannot login anymore (should it still be visible in page?)
        // TODO-FUNC UC-1 allow admin to edit entity info
        // as admin following actions: edit entity name, edit user (name, password, email), invitation actions, disable users, disable entity, transfer admin account
        // as normal user following actions: edit user (name, password, email), disable user (only himself)
        System.out.println("Removing user: " + userId);
        return PageLinks.PROFILE.redirect();
    }

    @ResponseBody
    @RequestMapping("/invitation_action/{tokenId}")
    @NavigateTo(PageLinks.PROFILE)
    // TODO-FUNC UC-1 make javascript show error message (also use this for regular error messages)
    public ResponseHolder invitationAction(@PathVariable String tokenId, @RequestParam String action, HttpServletRequest request) {
        Token token = null;
        switch (action) {
            case "extend":
                // TODO-FUNC UC-1 send email to user informing his invitation was extended? depends on whether we set time invitation valid in user invitation mail
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
        return ResponseHolder.success(token, messageUtil.translate("form.entity_settings." + action + "_invitation.success", 5));
    }

    @ResponseBody
    @RequestMapping("/transfer_admin_role/{userId}")
    @NavigateTo(PageLinks.PROFILE)
    public ResponseHolder transferAdminRole(@PathVariable String userId) {
        // TODO-FUNC UC-1 transfer admin role
        // TODO-BUG UC-1 logged in user (in context also needs to be updated)
        User user = userManager.transferAdminRole(currentUser(), userId);
        return ResponseHolder.success(user);
    }
}

// TODO write all tests for use cases
// annotate with pending if not yet implemented
// assign use case number
// say priority or type of test (main scenario, bugfix, ...)
// make report based on number of tests per use case
// look for todos in code assigned to specific use case