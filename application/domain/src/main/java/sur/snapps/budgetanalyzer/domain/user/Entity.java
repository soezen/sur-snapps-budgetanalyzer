package sur.snapps.budgetanalyzer.domain.user;

import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseEntity;

import javax.persistence.Table;
import java.util.Objects;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:45
 */
@javax.persistence.Entity
@Table(name = "ENTITIES")
@Audited
public class Entity extends BaseEntity {

    private String name;

    private boolean owned;
    private boolean shared;

//    private List<Account> accounts;
    // TODO when updating name to one that is already used, we get unexpected error
    // we would like to have a nice error message instead

    protected Entity() {
        // for hibernate
    }

    @Override
    public String getDisplayValue() {
        return name;
    }

    private Entity(Builder builder) {
        this.name = builder.name;
        this.shared = builder.shared;
        this.owned = builder.owned;
    }

    public static Builder newOwnedEntity() {
        return new Builder().owned(true).shared(false);
    }

    public String getName() {
        return name;
    }

    public boolean isOwned() {
        return owned;
    }

    public boolean isShared() {
        return shared;
    }

    public void updateName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Entity
                && Objects.equals(name, ((Entity) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public static class Builder {

        private String name;
        private boolean owned;
        private boolean shared;

        public Entity build() {
            return new Entity(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder shared(boolean shared) {
            this.shared = shared;
            return this;
        }

        public Builder owned(boolean owned) {
            this.owned = owned;
            return this;
        }

    }
}
