package sur.snapps.budgetanalyzer.domain.product;

import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author sur
 * @since 26/03/2015
 */
@Entity
@Table(name = "PRODUCT_TYPES")
public class ProductType extends BaseAuditedEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private ProductType() {};

    private ProductType(Builder builder) {
        this.category = builder.category;
        this.name = builder.name;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public Product.Builder createProduct() {
        return new Product.Builder(this);
    }

    @Override
    public String getDisplayValue() {
        return name;
    }

    public static class Builder {
        private String name;
        private Category category;

        public Builder(Category category) {
            this.category = category;
        }

        public ProductType build() {
            return new ProductType(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}
