package sur.snapps.budgetanalyzer.domain.util.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sur.snapps.budgetanalyzer.domain.user.User;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 21:37
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        rejectIfEmptyOrWhitespace(errors, "username", "error.field.required");
        rejectIfEmptyOrWhitespace(errors, "password", "error.field.required");
        rejectIfEmptyOrWhitespace(errors, "email", "error.field.required");

        if (!errors.hasFieldErrors("password")) {
            PasswordValidator passwordValidator = new PasswordValidator();
            if (!passwordValidator.validate(user.getPassword())) {
                errors.rejectValue("password", "error.user.password.incorrect_pattern");
            }
        }
        if (!errors.hasFieldErrors("email")) {
            EmailValidator emailValidator = new EmailValidator();
            if (!emailValidator.validate(user.getEmail())) {
                errors.rejectValue("email", "error.email.invalid");
            }
        }
    }
}
