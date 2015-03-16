package sur.snapps.budgetanalyzer.domain.user;

import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:45
 */
@javax.persistence.Entity
@Table(name = "ACCOUNTS")
@Audited
public class Account extends BaseAuditedEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    private Account() {}

    private Account(Builder builder) {
        this.name = builder.name;
        this.owner = builder.owner;
    }

    public String name() {
        return name;
    }

    public User owner() {
        return owner;
    }

    public static Builder newAccount() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private User owner;

        public Account build() {
            return new Account(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder owner(User owner) {
            this.owner = owner;
            return this;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDisplayValue() {
        return name;
    }
}
