package sur.snapps.budgetanalyzer.domain.user;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * User: SUR
 * Date: 6/04/14
 * Time: 10:24
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String username;
    private String password;
    private boolean enabled;
    @ElementCollection
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "USERNAME"))
    @Column(name = "AUTHORITY")
    // TODO add guava library
    private List<String> authorities = new ArrayList<>();

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
