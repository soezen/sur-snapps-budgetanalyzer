package sur.snapps.budgetanalyzer.business.exception;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * User: SUR
 * Date: 1/05/14
 * Time: 12:45
 */
public class BusinessException extends RuntimeException {

    protected final static Locale LOCALE_NL = new Locale("nl");

    private String errorCode;
    private String errorMessage;

    public BusinessException(String errorCode) {
        this.errorCode = errorCode;

        // TODO translate error code?
    }

    public BusinessException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String translateErrorMessage(ReloadableResourceBundleMessageSource messages) {
        return messages.getMessage(errorCode, new Object[0], messages.getMessage(errorMessage, new Object[0], errorMessage, LOCALE_NL), LOCALE_NL);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
