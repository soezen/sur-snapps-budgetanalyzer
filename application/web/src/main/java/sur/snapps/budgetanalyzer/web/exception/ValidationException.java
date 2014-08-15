package sur.snapps.budgetanalyzer.web.exception;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.FieldError;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;

import java.util.List;

/**
 * User: SUR
 * Date: 6/08/14
 * Time: 21:02
 */
public class ValidationException extends BusinessException {

    private List<FieldError> fieldErrors;

    public ValidationException(List<FieldError> fieldErrors) {
        super("form.errors.validation");
        this.fieldErrors = fieldErrors;
    }

    @Override
    public String translateErrorMessage(ReloadableResourceBundleMessageSource messages) {
        StringBuilder builder = new StringBuilder("<p>" + super.translateErrorMessage(messages));
        builder.append("<ul>");
        for (FieldError error : fieldErrors) {
            builder.append("<li>")
                    .append(error.getField())
                    .append(" : ")
                    .append(messages.getMessage(error.getCode(), new Object[0], error.getDefaultMessage(), LOCALE_NL))
                    .append("</li>");
        }
        builder.append("</ul></p>");
        return builder.toString();
    }
}
