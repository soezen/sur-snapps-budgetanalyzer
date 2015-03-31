package sur.snapps.budgetanalyzer.domain.store;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author sur
 * @since 26/03/2015
 */
@Embeddable
public class Address {

    private String street;
    private String number;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    private String city;
    private String country;

    // necessary for jpa
    private Address() {}

    private Address(Builder builder) {
        this.street = builder.street;
        this.number = builder.number;
        this.zipCode = builder.zipCode;
        this.city = builder.city;
        this.country = builder.country;
    }

    public String street() {
        return street;
    }

    public String number() {
        return number;
    }

    public String zipCode() {
        return zipCode;
    }

    public String city() {
        return city;
    }

    public String country() {
        return country;
    }

    public static Builder newAddress() {
        return new Builder();
    }

    public static class Builder {
        private String street;
        private String number;
        private String zipCode;
        private String city;
        private String country;

        public Address build() {
            return new Address(this);
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }
    }
}
