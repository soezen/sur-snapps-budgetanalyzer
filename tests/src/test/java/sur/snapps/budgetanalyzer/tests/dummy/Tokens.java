package sur.snapps.budgetanalyzer.tests.dummy;

import sur.snapps.jetta.database.dummy.DummyExtractor;

/**
 * User: SUR
 * Date: 5/06/14
 * Time: 6:59
 */
public class Tokens {

    private static DummyToken valid;
    private static DummyToken expired;
    private static DummyToken revoked;

    public static DummyToken valid() {
        if (valid == null) {
            valid = new DummyExtractor("objects/entity_ateam.xml")
                .get(DummyToken.class, new String[] {"value", "token-valid"});
        }
        return valid;
    }

    public static DummyToken expired() {
        if (expired == null) {
            expired = new DummyExtractor("objects/entity_ateam.xml")
                    .get(DummyToken.class, new String[] {"value", "token-expired"});
        }
        return expired;
    }

    public static DummyToken revoked() {
        if (revoked == null) {
            revoked = new DummyExtractor("objects/entity_ateam.xml")
                    .get(DummyToken.class, new String[] { "value", "token-revoked"});
        }
        return revoked;
    }
}
