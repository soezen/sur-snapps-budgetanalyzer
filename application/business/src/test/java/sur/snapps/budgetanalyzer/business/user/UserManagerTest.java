package sur.snapps.budgetanalyzer.business.user;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.annotation.Dummy;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.unitils.easymock.EasyMockUnitils.replay;

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
    @InjectIntoByType
    private TokenManager tokenManager;

    @Mock
    private User user;
    @Mock
    private Token token;
    @Dummy
    private Entity entity;

    @Test
    public void testCreateUserAdmin() {
        Capture<Entity> entityCapture = new Capture<>();
        String username = "username";

        expect(user.getTokenValue()).andReturn(null);
        expect(user.getName()).andReturn(username);
        user.encodePassword();
        user.setEnabled(true);
        user.addAuthority(User.ROLE_USER);
        user.addAuthority(User.ROLE_ADMIN);
        user.setEntity(capture(entityCapture));
        expect(repository.save(user)).andReturn(user);
        replay();

        User result = manager.create(user);

        assertNotNull(result);
        assertSame(user, result);
        Entity entity = entityCapture.getValue();
        assertEquals(username, entity.getName());
        assertTrue(entity.isOwned());
        assertFalse(entity.isShared());
    }

    @Test
    public void testCreateUserNotAdmin() {
        expect(user.getTokenValue()).andReturn("token");
        expect(tokenManager.findTokenByValue("token")).andReturn(token);
        expect(token.entity()).andReturn(entity);
        user.setEntity(entity);
        tokenManager.delete(token);
        user.encodePassword();
        user.setEnabled(true);
        user.addAuthority(User.ROLE_USER);
        expect(repository.save(user)).andReturn(user);
        replay();

        User result = manager.create(user);

        assertNotNull(result);
        assertSame(user, result);
    }

    @Test
    public void testIsUsernameUsed() {
        String username = "username";
        expect(repository.isUsernameUsed(username)).andReturn(true);
        replay();

        assertTrue(manager.isUsernameUsed(username));
    }

    @Test
    public void testFindByUsername() {
        String username = "username";

        expect(repository.findByUsername(username)).andReturn(user);
        replay();

        assertSame(user, manager.findByUsername(username));
    }
}
