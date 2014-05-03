package sur.snapps.budgetanalyzer.domain.user;

import sur.snapps.budgetanalyzer.util.DateUtil;

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
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private TokenStatus status;

    protected Token() {
        // for hibernate
    }

    private Token(Builder builder) {
        value = builder.token;
        entity = builder.entity;
        expirationDate = builder.expirationDate;
        email = builder.email;
        status = builder.status;
    }

    public static Token.Builder createUserInvitationToken() {
        return new Builder().daysValid(7).status(TokenStatus.VALID);
    }

    public static class Builder {

        private String token;
        private Entity entity;
        private Date expirationDate;
        private String email;
        private TokenStatus status;

        public Token build() {
            // TODO add validation
            // if status is valid, expiration date has to be after now
            // (automatically change it or throw error)
            return new Token(this);
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder status(TokenStatus status) {
            this.status = status;
            return this;
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
            java.util.Date now = DateUtil.now();
            now = DateUtil.removeTime(now);
            this.expirationDate = new Date(DateUtil.addDays(now, days).getTime());
            return this;
        }
    }

    public String value() {
        return value;
    }

    public TokenStatus getStatus() {
        if (status.isValid() && hasExpired()) {
            status = TokenStatus.EXPIRED;
        }
        return status;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasExpired() {
        return getExpirationDate().before(new java.util.Date());
    }

    public Entity entity() {
        return entity;
    }

    public java.util.Date getExpirationDate() {
        return new java.util.Date(expirationDate.getTime());
    }
}
