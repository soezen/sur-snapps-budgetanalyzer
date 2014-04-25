package sur.snapps.budgetanalyzer.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;
import sur.snapps.budgetanalyzer.domain.user.User;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 7:18
 */
public class UserTest {

    // TODO use unitils injection
    // TODO test getters and setters automatically
    private User user = new User();

    @Test
    public void testGetUsername() {
        assertNull(user.getUsername());
        user.setUsername("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertNull(user.getPassword());
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }
}
