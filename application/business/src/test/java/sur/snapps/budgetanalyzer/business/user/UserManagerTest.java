package sur.snapps.budgetanalyzer.business.user;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * User: SUR
 * Date: 28/04/14
 * Time: 20:11
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserManagerTest {

    @TestedObject
    private UserManager manager;

    @Mock
    @InjectIntoByType
    private UserRepository repository;

    @Mock
    private User user;

    @Test
    public void testCreateUser() {
        Capture<Entity> entityCapture = new Capture<>();
        String username = "username";

        expect(user.getUsername()).andReturn(username);
        user.encodePassword();
        user.setEnabled(true);
        user.setAdmin(true);
        user.addAuthority("ROLE_USER");
        user.setEntity(capture(entityCapture));
        expect(repository.save(user)).andReturn(user);
        replay();

        User result = manager.createUser(user);

        assertNotNull(result);
        assertSame(user, result);
        Entity entity = entityCapture.getValue();
        assertEquals(username, entity.getName());
        assertTrue(entity.isOwned());
        assertFalse(entity.isShared());
    }
}
