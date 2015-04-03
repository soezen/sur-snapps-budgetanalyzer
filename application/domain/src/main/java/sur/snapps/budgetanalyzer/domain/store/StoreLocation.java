package sur.snapps.budgetanalyzer.domain.store;

import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TODO find better name for this class.
 *
 * @author sur
 * @since 26/03/2015
 */
@Entity
@Table(name = "STORE_LOCATIONS")
@Audited
public class StoreLocation extends BaseAuditedEntity {

    private String name;

    @Embedded
    private Address address;

    // necessary for jpa
    private StoreLocation() {}

    private StoreLocation(Builder builder) {
        this.name = builder.name;
        this.address = builder.address;
    }

    public String name() {
        return name;
    }

    public Address address() {
        return address;
    }

    public static Builder newStoreLocation() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Address address;

        public StoreLocation build() {
            return new StoreLocation(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }
    }

    @Override
    public String getDisplayValue() {
        return name;
    }
}
