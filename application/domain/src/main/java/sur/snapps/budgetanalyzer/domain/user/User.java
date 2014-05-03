package sur.snapps.budgetanalyzer.domain.user;

import com.google.common.collect.Lists;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:24
 */
@javax.persistence.Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    private boolean admin;

    @Transient
    private String tokenValue;

    @ElementCollection
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "AUTHORITY")
    private List<String> authorities = Lists.newArrayList();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ENTITY_ID")
    private Entity entity;

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public void encodePassword() {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        String encodedPassword = passwordEncoder.encodePassword(password, "BUDGET-ANALYZER");
        setPassword(encodedPassword);
    }
}
