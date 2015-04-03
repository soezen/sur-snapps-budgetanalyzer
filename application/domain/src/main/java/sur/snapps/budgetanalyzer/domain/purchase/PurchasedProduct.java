package sur.snapps.budgetanalyzer.domain.purchase;

import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;
import sur.snapps.budgetanalyzer.domain.product.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author sur
 * @since 26/03/2015
 */
@Entity
@Table(name = "PURCHASED_PRODUCTS")
@Audited
public class PurchasedProduct extends BaseAuditedEntity {

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    private double amount;
    @Column(name = "UNIT_PRICE")
    private double unitPrice;

    private PurchasedProduct() {}

    private PurchasedProduct(Builder builder) {
        this.product = builder.product;
        this.amount = builder.amount;
        this.unitPrice = builder.unitPrice;
    }

    public Product product() {
        return product;
    }

    public double amount() {
        return amount;
    }

    public double unitPrice() {
        return unitPrice;
    }

    public static Builder newPurchasedProduct() {
        return new Builder();
    }

    @Override
    public String getDisplayValue() {
        return null;
    }

    public static class Builder {
        private Product product;
        private double amount;
        private double unitPrice;

        public PurchasedProduct build() {
            return new PurchasedProduct(this);
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder unitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }
    }
}
