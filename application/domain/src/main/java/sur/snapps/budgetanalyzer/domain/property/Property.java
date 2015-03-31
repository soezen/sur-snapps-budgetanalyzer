package sur.snapps.budgetanalyzer.domain.property;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:46
 */
public class Property {

    private String value;
    private PropertyType type;

    private Property(Builder builder) {
        this.type = builder.type;
        this.value = builder.value;
    }

    public PropertyType type() {
        return type;
    }

    public String value() {
        return value;
    }

    public static class Builder {
        private String value;
        private PropertyType type;

        public Builder(PropertyType type) {
            this.type = type;
        }

        public Property build() {
            return new Property(this);
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }
    }
}
