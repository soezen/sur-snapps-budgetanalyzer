package sur.snapps.budgetanalyzer.business.transaction;

/**
 * @author sur
 * @since 01/04/2015
 */
public class EditPaymentView {

    private String accountId;
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
