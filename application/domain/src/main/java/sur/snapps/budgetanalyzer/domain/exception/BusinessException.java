package sur.snapps.budgetanalyzer.domain.exception;

/**
 * User: SUR
 * Date: 1/05/14
 * Time: 12:45
 */
public class BusinessException extends RuntimeException {

    private String errorCode;

    public BusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
