package sur.snapps.budgetanalyzer.web.response;

/**
 * User: SUR
 * Date: 15/06/14
 * Time: 20:14
 */
public class SuccessResponse<T> implements ResponseHolder<T> {

    private T value;

    public SuccessResponse(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
