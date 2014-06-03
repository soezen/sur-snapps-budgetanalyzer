package sur.snapps.budgetanalyzer.domain.user;

/**
 * User: SUR
 * Date: 3/05/14
 * Time: 9:59
 */
public enum TokenStatus {

    VALID,
    REVOKED,
    EXPIRED,
    NOT_EXISTING;

    public boolean isNotExisting() {
        return this == NOT_EXISTING;
    }

    public boolean isValid() {
        return this == VALID;
    }

    public boolean isRevoked() {
        return this == REVOKED;
    }

    public boolean isExpired() {
        return this == EXPIRED;
    }
}
