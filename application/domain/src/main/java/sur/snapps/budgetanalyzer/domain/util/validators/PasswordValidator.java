package sur.snapps.budgetanalyzer.domain.util.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (		    	# Start of group
 *  (?=.*\d)		#   must contains one digit from 0-9
 *  (?=.*[a-z])		#   must contains one lowercase characters
 *  (?=.*[A-Z])		#   must contains one uppercase characters
 *  (?=.*[@#$%])	#   must contains one special symbols in the list "@#$%"
 *         .		#     match anything with previous condition checking
 *          {6,20}	#        length at least 6 characters and maximum of 20
 * )    			# End of group
 *
 * User: SUR
 * Date: 25/04/14
 * Time: 21:32
 */
public class PasswordValidator {

    private Pattern pattern;

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public boolean validate(String password) {
        return pattern.matcher(password).matches();
    }
}
