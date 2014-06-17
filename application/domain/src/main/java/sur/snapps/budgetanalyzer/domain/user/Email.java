package sur.snapps.budgetanalyzer.domain.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * User: SUR
 * Date: 15/06/14
 * Time: 13:46
 */
@Embeddable
public class Email {

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
