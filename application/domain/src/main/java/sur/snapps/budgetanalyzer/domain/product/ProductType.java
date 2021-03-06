package sur.snapps.budgetanalyzer.domain.product;

import org.hibernate.envers.Audited;
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
@Audited
public class ProductType extends BaseAuditedEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    // TODO add total value per category in chart

    protected ProductType() {}

    private ProductType(Builder builder) {
        setId(builder.id);
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
        private String id;
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

        public Builder id(String id) {
            this.id = id;
            return this;
        }
    }
}
