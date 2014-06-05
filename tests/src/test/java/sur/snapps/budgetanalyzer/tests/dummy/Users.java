package sur.snapps.budgetanalyzer.tests.dummy;

import sur.snapps.jetta.database.dummy.DummyExtractor;

/**
 * User: SUR
 * Date: 5/06/14
 * Time: 6:59
 */
public class Users {

    private static DummyUser hannibal;
    private static DummyUser face;

    public static DummyUser hannibal() {
        if (hannibal == null) {
            hannibal = new DummyExtractor("objects/entity_ateam.xml")
                .get(DummyUser.class,
                        new String[] {"username", "hannibal"});
        }
        return hannibal;
    }

    public static DummyUser face() {
        if (face == null) {
            face = new DummyExtractor("objects/entity_ateam.xml")
                    .get(DummyUser.class, new String[] {"username", "face"});
        }
        return face;
    }
}
