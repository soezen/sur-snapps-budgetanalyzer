package sur.snapps.budgetanalyzer.domain.store;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author sur
 * @since 17/03/2015
 */
@Entity
@Table(name = "STORES")
public class Store extends BaseAuditedEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private StoreType type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "STORE_ID")
    private List<StoreLocation> locations;

    private Store() {
        // necessary for jpa
    }

    // TODO default select store location closest to user address after selecting store

    private Store(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.locations = Lists.newArrayList();
    }

    public String name() {
        return name;
    }

    public StoreType type() {
        return type;
    }

    public List<StoreLocation> locations() {
        return ImmutableList.copyOf(locations);
    }

    public void addLocation(StoreLocation location) {
        locations.add(location);
    }

    public static Builder newStore() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private StoreType type;

        public Store build() {
            return new Store(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(StoreType type) {
            this.type = type;
            return this;
        }
    }

    @Override
    public String getDisplayValue() {
        return name;
    }

    public String getName() {
        return name;
    }
}
