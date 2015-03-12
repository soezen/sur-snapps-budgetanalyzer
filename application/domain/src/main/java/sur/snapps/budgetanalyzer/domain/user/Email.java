package sur.snapps.budgetanalyzer.domain.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * User: SUR
 * Date: 15/06/14
 * Time: 13:46
 */
@Embeddable
public class Email {

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String address;

    protected Email() {}

    private Email(Builder builder) {
        this.address = builder.address;
    }

    public static Email create(String address) {
        return new Builder()
            .address(address)
            .build();
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private String address;

        public Email build() {
            Email email = new Email(this);
            return email;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }
    }

    public String address() {
        return address;
    }

    /*************************
     * GETTERS FOR FRONTEND *
     ************************/

    public String getAddress() {
        return address;
    }
}
