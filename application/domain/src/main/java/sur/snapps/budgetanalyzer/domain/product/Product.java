package sur.snapps.budgetanalyzer.domain.product;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;
import sur.snapps.budgetanalyzer.domain.property.Property;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:44
 */
@Entity
@Table(name = "PRODUCTS")
@Audited
public class Product extends BaseAuditedEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private ProductType type;

    @Transient
    private List<Property> properties;

    private Product() {}

    private Product(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.properties = builder.properties;
    }

    public String name() {
        return name;
    }

    public ProductType type() {
        return type;
    }

    public List<Property> properties() {
        return ImmutableList.copyOf(properties);
    }

    @Override
    public String getDisplayValue() {
        return name;
    }

    public static class Builder {
        private String name;
        private ProductType type;
        private List<Property> properties;

        public Builder(ProductType type) {
            this.type = type;
            this.properties = Lists.newArrayList();
        }

        public Product build() {
            return new Product(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder property(Property property) {
            properties.add(property);
            return this;
        }
    }

    public String getName() {
        return name;
    }
}
