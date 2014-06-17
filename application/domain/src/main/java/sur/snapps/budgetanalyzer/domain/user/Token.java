package sur.snapps.budgetanalyzer.domain.user;

import sur.snapps.budgetanalyzer.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:41
 */
@javax.persistence.Entity
@Table(name = "TOKENS")
public class Token implements Serializable {

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
    @Embedded
    private Email email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenStatus status;
    @Enumerated(EnumType.STRING)
    private TokenType type;

    protected Token() {
        // for hibernate
    }

    private Token(Builder builder) {
        value = builder.token;
        entity = builder.entity;
        expirationDate = builder.expirationDate;
        email = builder.email;
        status = builder.status;
        type = builder.type;
    }

    public static Token.Builder createUserInvitationToken() {
        return new Builder()
                .type(TokenType.USER_INVITATION)
                .daysValid(7)
                .status(TokenStatus.VALID);
    }

    public void extendWithDays(int days) {
        this.expirationDate = new Date(DateUtil.addDays(expirationDate, days).getTime());
    }

    public void revoke() {
        this.status = TokenStatus.REVOKED;
    }

    public void restore() {
        if (hasExpired()) {
            this.expirationDate = new Date(DateUtil.addDays(DateUtil.now(), 7).getTime());
        }
        this.status = TokenStatus.VALID;
    }

    public static class Builder {

        private String token;
        private Entity entity;
        private Date expirationDate;
        private Email email;
        private TokenStatus status;
        private TokenType type;

        public Token build() {
            // TODO add validation
            // if status is valid, expiration date has to be after now
            // (automatically change it or throw error)
            return new Token(this);
        }

        public Builder type(TokenType type) {
            this.type = type;
            return this;
        }

        public Builder email(Email email) {
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

    public int getId() {
        return id;
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

    public Email getEmail() {
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
