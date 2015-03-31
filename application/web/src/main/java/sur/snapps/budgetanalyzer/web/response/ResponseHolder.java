package sur.snapps.budgetanalyzer.web.response;

/**
 * User: SUR
 * Date: 15/06/14
 * Time: 20:13
 */
public class ResponseHolder {

    private boolean success;
    private String message;
    private Object value;

    private ResponseHolder(Builder builder) {
        this.success = builder.success;
        this.message = builder.message;
        this.value = builder.value;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getValue() {
        return value;
    }

    public static ResponseHolder success(Object value) {
        return new Builder(true).value(value).build();
    }

    public static ResponseHolder success(Object value, String message) {
        return new Builder(true).value(value).message(message).build();
    }

    public static ResponseHolder failure(String message) {
        return new Builder(false).message(message).build();
    }

    private static class Builder {
        private boolean success;
        private String message;
        private Object value;

        public Builder(boolean success) {
            this.success = success;
        }

        public ResponseHolder build() {
            return new ResponseHolder(this);
        }

        public Builder value(Object value) {
            this.value = value;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }
    }
}
