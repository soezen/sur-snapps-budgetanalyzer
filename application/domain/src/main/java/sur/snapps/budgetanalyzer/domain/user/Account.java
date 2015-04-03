package sur.snapps.budgetanalyzer.domain.user;

import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;
import sur.snapps.budgetanalyzer.domain.purchase.Payment;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private AccountType type;
    private String name;
    private double balance;

    // TODO when type is CHEQUE we need to specify whether only certain amounts are possible

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    private Account() {}

    private Account(Builder builder) {
        this.name = builder.name;
        this.owner = builder.owner;
        this.type = builder.type;
        this.balance = 0;
    }

    public void registerPayment(Payment payment) {
        balance -= payment.amount();
    }

    public String name() {
        return name;
    }

    public AccountType type() {
        return type;
    }

    public double balance() {
        return balance;
    }

    public User owner() {
        return owner;
    }

    public static Builder newAccount() {
        return new Builder();
    }

    public static class Builder {
        private AccountType type;
        private String name;
        private User owner;

        public Account build() {
            return new Account(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(AccountType type) {
            this.type = type;
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

    public AccountType getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner.getDisplayValue();
    }

    @Override
    public String getDisplayValue() {
        return name;
    }
}
