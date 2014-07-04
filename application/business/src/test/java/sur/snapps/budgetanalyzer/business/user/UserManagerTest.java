package sur.snapps.budgetanalyzer.business.user;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
    private EditUserView editUserView;
    @Mock
    private Token token;
    @Dummy
    private Entity entity;
    @Dummy
    private User user;

    @Test
    public void testCreateUserAdmin() {
        Capture<User> userCapture = new Capture<>();
        String username = "username";
        String name = "name";
        String email = "email";
        String password = "password";

        expect(editUserView.getUsername()).andReturn(username);
        expect(editUserView.getName()).andReturn(name);
        expect(editUserView.getEmail()).andReturn(email);
        expect(editUserView.getNewPassword()).andReturn(password);
        expect(editUserView.getTokenValue()).andReturn(null);
        expect(repository.save(capture(userCapture))).andReturn(user);
        replay();

        User result = manager.create(editUserView);

        assertNotNull(result);
        User userSaved = userCapture.getValue();
        assertNotNull(userSaved);
        assertEquals(username, userSaved.getUsername());
        assertEquals(name, userSaved.getName());
        assertEquals(email, userSaved.getEmail().getAddress());
        assertNotNull(userSaved.getPassword());
        assertFalse(password.equals(userSaved.getPassword()));
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        assertTrue(passwordEncoder.isPasswordValid(userSaved.getPassword(), password, "BUDGET-ANALYZER"));

        Entity entity = userSaved.getEntity();
        assertEquals(name, entity.getName());
        assertTrue(entity.isOwned());
        assertFalse(entity.isShared());
    }

    @Test
    public void testCreateUserNotAdmin() {
        Capture<User> userCapture = new Capture<>();
        String username = "username";
        String name = "name";
        String email = "email";
        String password = "password";
        String tokenValue = "token-value";

        expect(editUserView.getUsername()).andReturn(username);
        expect(editUserView.getName()).andReturn(name);
        expect(editUserView.getEmail()).andReturn(email);
        expect(editUserView.getNewPassword()).andReturn(password);
        expect(editUserView.getTokenValue()).andReturn(tokenValue);
        expect(tokenManager.findTokenByValue(tokenValue)).andReturn(token);
        expect(token.entity()).andReturn(entity);
        tokenManager.delete(token);
        expect(repository.save(capture(userCapture))).andReturn(user);
        replay();

        User result = manager.create(editUserView);

        assertNotNull(result);
        User userSaved = userCapture.getValue();
        assertNotNull(userSaved);
        assertEquals(username, userSaved.getUsername());
        assertEquals(name, userSaved.getName());
        assertEquals(email, userSaved.getEmail().getAddress());
        assertNotNull(userSaved.getPassword());
        assertFalse(password.equals(userSaved.getPassword()));
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        assertTrue(passwordEncoder.isPasswordValid(userSaved.getPassword(), password, "BUDGET-ANALYZER"));
        assertSame(entity, userSaved.getEntity());
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
