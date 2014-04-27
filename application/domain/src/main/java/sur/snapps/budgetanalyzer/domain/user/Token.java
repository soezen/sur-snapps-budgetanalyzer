package sur.snapps.budgetanalyzer.domain.user;

import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.UUID;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:41
 */
@javax.persistence.Entity
@Table(name = "TOKENS")
public class Token {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String token;
    @ManyToOne
    @JoinColumn(columnDefinition = "ENTITY_ID", nullable = false)
    private Entity entity;
    @Column(name = "EXPIRATION_DATE", nullable = false)
    private Date expirationDate;

    public String getToken() {
        return token;
    }

    public void generateToken() {
        token = UUID.randomUUID().toString();
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public DateTime getExpirationDate() {
        return new DateTime(expirationDate);
    }

    public void setExpirationDate(DateTime expirationDate) {
        if (expirationDate == null) {
            this.expirationDate = null;
        }
        this.expirationDate = new Date(expirationDate.getMillis());
    }
}
