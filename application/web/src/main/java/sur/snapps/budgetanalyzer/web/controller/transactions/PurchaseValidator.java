package sur.snapps.budgetanalyzer.web.controller.transactions;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sur.snapps.budgetanalyzer.business.transaction.EditPurchaseView;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 21:37
 */
public class PurchaseValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return EditPurchaseView.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EditPurchaseView purchase = (EditPurchaseView) obj;
        rejectIfEmptyOrWhitespace(errors, "date", "error.field.required");
        rejectIfEmptyOrWhitespace(errors, "storeLocationId", "error.field.required");

        System.out.println("products: " + purchase.getProducts());
        System.out.println("size: " + purchase.getProducts().size());
        if (purchase.getProducts() == null || purchase.getProducts().isEmpty()) {
            errors.reject("products", "error.purchase.invalid.no_products");
        } else {
            // TODO check validity of all products
        }
    }
}
