package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sur.snapps.budgetanalyzer.business.exception.BusinessException;
import sur.snapps.budgetanalyzer.business.user.EditUserView;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.TokenStatus;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

import javax.validation.Valid;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:41
 */
@Controller
public class UserRegistrationController extends AbstractController {

    // TODO selenium tests

    @Autowired
    private UserManager userManager;
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping("/userRegistration")
    public String openUserRegistrationPage(Model model) {
        model.addAttribute("user", new EditUserView());
        return PageLinks.USER_REGISTRATION.page();
    }

    @RequestMapping("/userRegistrationSuccess")
    public String openUserRegistrationSuccessPage() {
        return PageLinks.USER_REGISTRATION_SUCCESS.page();
    }

    @RequestMapping(value = "/userRegistrationWithToken")
    public String openUserRegistrationWithTokenPage(Model model, @RequestParam String value) {
        Token token = tokenManager.findTokenByValue(value);

        TokenStatus status = token == null ? TokenStatus.NOT_EXISTING : token.getStatus();
        if (!status.isValid()) {
            model.addAttribute("error", true);
            model.addAttribute("error_message", "form.user_registration.token." + status);
            return PageLinks.INVALID_TOKEN.page();
        }

        EditUserView user = new EditUserView(token);

        model.addAttribute("user", user);
        return PageLinks.USER_REGISTRATION.page();
    }

    @RequestMapping(value = "/postUserRegistration", method = RequestMethod.POST)
    @NavigateTo(value = PageLinks.USER_REGISTRATION, successMessage = "form.user_registration.success")
    public String userRegistration(@ModelAttribute("user") @Valid EditUserView user, BindingResult bindingResult) {
        validateUserRegistrationInput(user, bindingResult);

        if (bindingResult.hasErrors()) {
//            return PageLinks.USER_REGISTRATION.page();
            throw new BusinessException("form.errors.validation");
        }

        userManager.create(user);
        return PageLinks.USER_REGISTRATION.redirect();
    }

    private void validateUserRegistrationInput(EditUserView user, Errors errors) {

        userValidator.validate(user, errors);

        if (!errors.hasFieldErrors("newPassword")) {
            rejectIfEmptyOrWhitespace(errors, "newPassword", "error.field.required");
        }
        if (!errors.hasFieldErrors("confirmPassword")) {
            rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.field.required");
        }

        // TODO-FUNC UC-1 add validation: if !admin -> tokenValue required

        if (!errors.hasFieldErrors("username")
                && userManager.isUsernameUsed(user.getUsername())) {
            errors.rejectValue("username", "error.user.username_already_used");
        }
    }
}
