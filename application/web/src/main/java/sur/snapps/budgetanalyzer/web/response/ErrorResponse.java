package sur.snapps.budgetanalyzer.web.response;

/**
 * User: SUR
 * Date: 15/06/14
 * Time: 20:16
 */
public class ErrorResponse<T> implements ResponseHolder<T> {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
