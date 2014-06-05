package sur.snapps.budgetanalyzer.tests.dummy;

import sur.snapps.jetta.database.dummy.DummyExtractor;

/**
 * User: SUR
 * Date: 5/06/14
 * Time: 6:59
 */
public class Users {

    private static DummyExtractor extractor = new DummyExtractor("objects/entity_ateam.xml");

    private static DummyUser hannibal;
    private static DummyUser face;

    public static DummyUser hannibal() {
        if (hannibal == null) {
            hannibal = extractor.get(DummyUser.class, "hannibal");
        }
        return hannibal;
    }

    public static DummyUser face() {
        if (face == null) {
            face = extractor.get(DummyUser.class, "face");
        }
        return face;
    }
}
