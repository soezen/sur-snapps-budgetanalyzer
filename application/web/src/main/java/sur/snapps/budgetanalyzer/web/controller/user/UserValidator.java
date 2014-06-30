package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sur.snapps.budgetanalyzer.business.user.EditUserView;
import sur.snapps.budgetanalyzer.util.validators.EmailValidator;
import sur.snapps.budgetanalyzer.util.validators.PasswordValidator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 21:37
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return EditUserView.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EditUserView user = (EditUserView) obj;
        rejectIfEmptyOrWhitespace(errors, "name", "error.field.required");
        rejectIfEmptyOrWhitespace(errors, "username", "error.field.required");
        rejectIfEmptyOrWhitespace(errors, "email", "error.field.required");

        if (!errors.hasFieldErrors("email")) {
            EmailValidator emailValidator = new EmailValidator();
            if (!emailValidator.validate(user.getEmail())) {
                errors.rejectValue("email", "error.email.invalid");
            }
        }

        if (user.getConfirmPassword() != null || user.getNewPassword() != null) {
            rejectIfEmptyOrWhitespace(errors, "newPassword", "error.field.required");
            rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.field.required");

            if (!errors.hasFieldErrors("newPassword") && !errors.hasFieldErrors("confirmPassword")) {
                if (!user.getNewPassword().equals(user.getConfirmPassword())) {
                    errors.rejectValue("confirmPassword", "error.user.password.incorrect_confirm_password");
                }

                PasswordValidator passwordValidator = new PasswordValidator();
                if (!passwordValidator.validate(user.getNewPassword())) {
                    errors.rejectValue("newPassword", "error.user.password.incorrect_pattern");
                }

            }
        }

    }
}
