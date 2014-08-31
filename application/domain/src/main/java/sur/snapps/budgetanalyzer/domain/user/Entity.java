package sur.snapps.budgetanalyzer.domain.user;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:45
 */
@javax.persistence.Entity
@Table(name = "ENTITIES")
public class Entity implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private boolean owned;
    private boolean shared;

//    private List<Account> accounts;

    protected Entity() {
        // for hibernate
    }

    private Entity(Builder builder) {
        this.name = builder.name;
        this.shared = builder.shared;
        this.owned = builder.owned;
    }

    public static Builder newOwnedEntity() {
        return new Builder().owned(true).shared(false);
    }

    public int getId() {
        return id;
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
                && Objects.equals(id, ((Entity) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
