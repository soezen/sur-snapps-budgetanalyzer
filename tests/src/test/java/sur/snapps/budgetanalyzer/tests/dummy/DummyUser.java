package sur.snapps.budgetanalyzer.tests.dummy;

import sur.snapps.jetta.database.dummy.Identifier;
import sur.snapps.jetta.database.dummy.annotations.Calculated;
import sur.snapps.jetta.database.dummy.annotations.Dummy;

/**
 * User: SUR
 * Date: 5/06/14
 * Time: 7:01
 */
@Dummy("users/user")
public class DummyUser {

    private int id;
    @Identifier
    private String username;
    private String password;
    private String email;
    private String name;
    private boolean enabled;
    @Calculated(selector = "authorities/authority", fk = "user_id", id = "id", formula = "authority/text() = 'ROLE_ADMIN'")
    private boolean admin;
    private int entityId;

    public int id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public boolean enabled() {
        return enabled;
    }

    public boolean admin() {
        return admin;
    }

    public int entityId() {
        return entityId;
    }
}
