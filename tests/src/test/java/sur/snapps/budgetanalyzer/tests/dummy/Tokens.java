package sur.snapps.budgetanalyzer.tests.dummy;

import sur.snapps.jetta.database.dummy.DummyExtractor;

/**
 * User: SUR
 * Date: 5/06/14
 * Time: 6:59
 */
public class Tokens {

    private static DummyExtractor extractor = new DummyExtractor("objects/entity_ateam.xml");

    private static DummyToken valid;
    private static DummyToken validRevoked;
    private static DummyToken validExtended;
    private static DummyToken expired;
    private static DummyToken revoked;

    public static DummyToken valid() {
        if (valid == null) {
            valid = extractor.get(DummyToken.class, "token-valid");
        }
        return valid;
    }

    public static DummyToken validRevoked() {
        if (validRevoked == null) {
            validRevoked = extractor.doAction(valid(), "revoke");
        }
        return validRevoked;
    }

    public static DummyToken validExtended() {
        if (validExtended == null) {
            validExtended = extractor.doAction(valid(), "extend");
        }
        return validExtended;
    }

    public static DummyToken expired() {
        if (expired == null) {
            expired = extractor.get(DummyToken.class, "token-expired");
        }
        return expired;
    }

    public static DummyToken revoked() {
        if (revoked == null) {
            revoked = extractor.get(DummyToken.class, "token-revoked");
        }
        return revoked;
    }
}
