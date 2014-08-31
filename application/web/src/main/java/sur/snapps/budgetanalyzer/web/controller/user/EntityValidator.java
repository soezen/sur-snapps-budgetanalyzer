package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sur.snapps.budgetanalyzer.business.user.EditEntityView;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 21:37
 */
public class EntityValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return EditEntityView.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EditEntityView user = (EditEntityView) obj;
        rejectIfEmptyOrWhitespace(errors, "name", "error.field.required");
    }
}
