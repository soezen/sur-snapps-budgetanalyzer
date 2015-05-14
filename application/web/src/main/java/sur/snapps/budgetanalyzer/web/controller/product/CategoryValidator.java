package sur.snapps.budgetanalyzer.web.controller.product;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sur.snapps.budgetanalyzer.business.product.EditCategoryView;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 21:37
 */
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return EditCategoryView.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "name", "error.field.required");
        // TODO parent needs to be a valid reference to an existing category
    }
}
