package sur.snapps.budgetanalyzer.web.controller.accounts;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sur.snapps.budgetanalyzer.business.account.EditAccountView;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 21:37
 */
public class AccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return EditAccountView.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EditAccountView account = (EditAccountView) obj;
        rejectIfEmptyOrWhitespace(errors, "name", "error.field.required");
    }
}
