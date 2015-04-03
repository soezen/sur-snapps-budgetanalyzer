package sur.snapps.budgetanalyzer.domain.purchase;

import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;
import sur.snapps.budgetanalyzer.domain.user.Account;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:45
 */
@Entity
@Table(name = "PAYMENTS")
@Audited
public class Payment extends BaseAuditedEntity {

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    private double amount;

    private Payment() {}

    private Payment(Builder builder) {
        this.account = builder.account;
        this.amount = builder.amount;
    }

    public Account account() {
        return account;
    }

    public double amount() {
        return amount;
    }

    public static Builder newPayment() {
        return new Builder();
    }

    @Override
    public String getDisplayValue() {
        return null;
    }

    public static class Builder {
        private Account account;
        private double amount;

        public Payment build() {
            Payment payment = new Payment(this);
            account.registerPayment(payment);
            return payment;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }
    }
}
