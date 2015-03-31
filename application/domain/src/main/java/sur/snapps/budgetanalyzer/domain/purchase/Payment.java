package sur.snapps.budgetanalyzer.domain.purchase;

import sur.snapps.budgetanalyzer.domain.user.Account;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:45
 */
public class Payment {

    private Account account;
    private double amount;

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

    public static class Builder {
        private Account account;
        private double amount;

        public Payment build() {
            return new Payment(this);
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
