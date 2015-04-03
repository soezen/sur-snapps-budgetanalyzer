package sur.snapps.budgetanalyzer.domain.product;

import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:44
 */
@Entity
@Table(name = "CATEGORIES")
@Audited
public class Category extends BaseAuditedEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT")
    private Category parent;

    private Category() {}

    private Category(Builder builder) {
        this.name = builder.name;
        this.parent = builder.parent;
    }

    public String name() {
        return name;
    }

    public Category parent() {
        return parent;
    }

    public ProductType.Builder createProductType() {
        return new ProductType.Builder(this);
    }

    public Builder createSubCategory() {
        return new Builder(this);
    }

    public static Builder newCategory() {
        return new Builder();
    }

    @Override
    public String getDisplayValue() {
        return name;
    }

    public static class Builder {
        private String name;
        private Category parent;

        public Builder() {}
        public Builder(Category parent) {
            this.parent = parent;
        }

        public Category build() {
            return new Category(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}
