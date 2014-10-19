package sur.snapps.tests.tests;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * User: SUR
 * Date: 5/05/14
 * Time: 17:09
 */
public class FormResponse {

    private boolean formError;
    private List<String> errorFields;

    private FormResponse() {
        errorFields = Lists.newArrayList();
    }

    public static FormResponse newResponse() {
        return new FormResponse();
    }

    public FormResponse formError(boolean formError) {
        this.formError = formError;
        return this;
    }

    public FormResponse fieldError(String fieldName) {
        errorFields.add(fieldName);
        return this;
    }

    public boolean isSuccess() {
        return !formError
                && errorFields.isEmpty();
    }

    public boolean hasFieldError(String fieldName) {
        return errorFields.contains(fieldName + ".errors");
    }
}
