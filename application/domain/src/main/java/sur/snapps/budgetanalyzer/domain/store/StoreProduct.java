package sur.snapps.budgetanalyzer.domain.store;

import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;
import sur.snapps.budgetanalyzer.domain.product.Product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author sur
 * @since 26/03/2015
 */
@Entity
@Table(name = "STORE_PRODUCTS")
public class StoreProduct extends BaseAuditedEntity {

    private String code;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    private StoreProduct() {}

    private StoreProduct(Builder builder) {
        this.code = builder.code;
        this.product = builder.product;
        this.store = builder.store;
    }

    public String code() {
        return code;
    }

    public Product product() {
        return product;
    }

    public Store store() {
        return store;
    }

    public static Builder newStoreProduct() {
        return new Builder();
    }

    @Override
    public String getDisplayValue() {
        return code;
    }

    public static class Builder {
        private String code;
        private Product product;
        private Store store;

        public StoreProduct build() {
            return new StoreProduct(this);
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder store(Store store) {
            this.store = store;
            return this;
        }
    }

    public String getCode() {
        return code;
    }

    public Product getProduct() {
        return product;
    }
}
