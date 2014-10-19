package sur.snapps.budgetanalyzer.domain.user;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:24
 */
@javax.persistence.Entity
@Table(name = "USERS")
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

    public boolean hasAccessTo(Token token) {
        return isAdmin()
            && token.entity().equals(entity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return Iterables.contains(authorities, ROLE_ADMIN);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(String authority) {
        authorities.add(authority);
    }

    public void removeAuthority(String authority) {
        authorities.remove(authority);
    }

    public void encodePassword() {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        String encodedPassword = passwordEncoder.encodePassword(password, "BUDGET-ANALYZER");
        setPassword(encodedPassword);
    }

    @Override
    public String getDisplayValue() {
        return name;
    }
}
