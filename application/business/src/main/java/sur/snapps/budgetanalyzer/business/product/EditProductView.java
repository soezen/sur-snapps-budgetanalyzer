package sur.snapps.budgetanalyzer.business.product;

/**
 * @author sur
 * @since 22/03/2015
 */
public class EditProductView {

    private String id;
    private Double unitPrice;
    private Double amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
