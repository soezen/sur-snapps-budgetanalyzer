package sur.snapps.budgetanalyzer.domain.property;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:47
 */
public class PropertyType {

    private String name;

    private PropertyType(Builder builder) {
        this.name = builder.name;
    }

    public String name() {
        return name;
    }

    public static Builder newPropertyType() {
        return new Builder();
    }

    public Property.Builder createProperty() {
        return new Property.Builder(this);
    }

    public static class Builder {
        private String name;

        public PropertyType build() {
            return new PropertyType(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}

