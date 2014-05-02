package sur.snapps.budgetanalyzer.util.validators;

import java.util.regex.Pattern;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:08
 */
public class EmailValidator {

    private Pattern pattern;

    private static final String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(PATTERN);
    }

    public boolean validate(String input) {
        return pattern.matcher(input).matches();
    }
}
