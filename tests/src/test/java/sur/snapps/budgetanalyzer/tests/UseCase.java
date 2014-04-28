package sur.snapps.budgetanalyzer.tests;

/**
 * User: SUR
 * Date: 27/04/14
 * Time: 14:33
 */
public enum UseCase {

    USER_REGISTRATION("01 - User Registration");

    private String name;

    private UseCase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
