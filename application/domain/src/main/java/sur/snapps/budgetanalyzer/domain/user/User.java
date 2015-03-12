package sur.snapps.budgetanalyzer.domain.user;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

import static sur.snapps.budgetanalyzer.util.EncryptionUtil.encrypt;

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:24
 */
@javax.persistence.Entity
@Table(name = "USERS")
@Audited
public class User extends BaseEntity {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Embedded
    private Email email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "AUTHORITY")
    private List<String> authorities = Lists.newArrayList();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ENTITY_ID")
    private Entity entity;

    // have protected constructor no-arg
    // have private constructor builder

    protected User() {}

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.encryptedPassword;
        this.name = builder.name;
        this.entity = builder.entity;
        this.email = builder.email;
        this.authorities = builder.authorities;
        this.enabled = true;
    }

    public boolean hasAccessTo(Token token) {
        return isAdmin()
            && token.entity().equals(entity);
    }

    public boolean isAdmin() {
        return Iterables.contains(authorities, ROLE_ADMIN);
    }

    public static Builder createAdmin() {
        return createUser()
            .withAdminRole();
    }

    public static Builder createUser() {
        return new Builder()
            .withUserRole();
    }

    public void updateEmail(String email) {
        this.email = Email.create(email);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePassword(String password) {
        if (password != null) {
            this.password = encrypt(password);
        }
    }

    public void transferAdminRoleTo(User newAdminUser) {
        authorities.remove(User.ROLE_ADMIN);
        newAdminUser.authorities.add(User.ROLE_ADMIN);
    }

    public static class Builder {
        private String name;
        private Email email;
        private Entity entity;
        private String username;
        private String encryptedPassword;
        private List<String> authorities = Lists.newArrayList();

        public User build() {
            User user = new User(this);
            // TODO validate?
            return user;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = Email.create(email);
            return this;
        }

        public Builder entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.encryptedPassword = encrypt(password);
            return this;
        }

        private Builder withAdminRole() {
            authorities.add(ROLE_ADMIN);
            return this;
        }

        private Builder withUserRole() {
            authorities.add(ROLE_USER);
            return this;
        }
    }

    public String name() {
        return name;
    }

    public Email email() {
        return email;
    }

    public Entity entity() {
        return entity;
    }

    public String username() {
        return username;
    }

    public String encryptedPassword() {
        return password;
    }

    public boolean enabled() {
        return enabled;
    }

    public List<String> authorities() {
        return ImmutableList.copyOf(authorities);
    }

    /************************
     * GETTERS FOR FRONTEND *
     ************************/

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return email.address();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getDisplayValue() {
        return name;
    }
}
