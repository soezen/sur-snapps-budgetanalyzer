package sur.snapps.budgetanalyzer.domain.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.TestedObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 7:18
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserTest {

    // TODO-TECH test getters and setters automatically
    // is it necessary to test getters and setters?
    @TestedObject
    private User user;

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
