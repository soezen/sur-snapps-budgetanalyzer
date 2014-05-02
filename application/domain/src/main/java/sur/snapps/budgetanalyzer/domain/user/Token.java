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
    private String value;
    @ManyToOne
    @JoinColumn(columnDefinition = "ENTITY_ID", nullable = false)
    private Entity entity;
    @Column(name = "EXPIRATION_DATE", nullable = false)
    private Date expirationDate;

    private Token(Builder builder) {
        value = builder.token;
        entity = builder.entity;
        expirationDate = builder.expirationDate;
    }

    public static Token.Builder createUserInvitationToken() {
        return new Builder().daysValid(7);
    }

    public static class Builder {

        private String token;
        private Entity entity;
        private Date expirationDate;

        public Token build() {
            // TODO add validation
            return new Token(this);
        }

        public Builder generateToken() {
            token = UUID.randomUUID().toString();
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public Builder daysValid(int days) {
            this.expirationDate = new Date(DateTime.now().plusDays(days).getMillis());
            return this;
        }
    }

    public String value() {
        return value;
    }

    public Entity entity() {
        return entity;
    }

    public DateTime expirationDate() {
        return new DateTime(expirationDate);
    }
}
