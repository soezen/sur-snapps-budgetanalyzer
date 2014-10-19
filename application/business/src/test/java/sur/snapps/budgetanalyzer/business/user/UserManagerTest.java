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
import sur.snapps.budgetanalyzer.util.exception.BusinessException;

import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.unitils.easymock.EasyMockUnitils.replay;
import static sur.snapps.budgetanalyzer.business.user.EditUserViewMother.antonioBanderas;
import static sur.snapps.budgetanalyzer.business.user.EditUserViewMother.michellePfeiffer;
import static sur.snapps.budgetanalyzer.business.user.EditUserViewMother.valid;

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

    @Dummy
    private User user;
    @Dummy
    private List<User> users;
    @Dummy
    private Entity entity;

    @Test
    public void testCreateUserAdmin() {
        Capture<User> userCapture = new Capture<>();
        EditUserView michelle = michellePfeiffer();

        expect(repository.save(capture(userCapture))).andReturn(user);
        replay();

        User result = manager.create(michelle);

        assertNotNull(result);
        User userSaved = userCapture.getValue();
        assertNotNull(userSaved);
        assertEquals(michelle.getUsername(), userSaved.getUsername());
        assertEquals(michelle.getName(), userSaved.getName());
        assertEquals(michelle.getEmail(), userSaved.getEmail().getAddress());
        assertNotNull(userSaved.getPassword());
        assertFalse(michelle.getNewPassword().equals(userSaved.getPassword()));
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        assertTrue(passwordEncoder.isPasswordValid(userSaved.getPassword(), michelle.getNewPassword(), "BUDGET-ANALYZER"));

        Entity entity = userSaved.getEntity();
        assertEquals(michelle.getName(), entity.getName());
        assertTrue(entity.isOwned());
        assertFalse(entity.isShared());
    }

    @Test
    public void testCreateUserNotAdmin() {
        Capture<User> userCapture = new Capture<>();
        EditUserView antonio = antonioBanderas();
        Token token = valid();

        expect(tokenManager.findTokenByValue(antonio.getTokenValue())).andReturn(token);
        tokenManager.delete(token);
        expect(repository.save(capture(userCapture))).andReturn(user);
        replay();

        User result = manager.create(antonio);

        assertNotNull(result);
        assertSame(user, result);

        User userSaved = userCapture.getValue();
        assertNotNull(userSaved);
        assertEquals(antonio.getUsername(), userSaved.getUsername());
        assertEquals(antonio.getName(), userSaved.getName());
        assertEquals(antonio.getEmail(), userSaved.getEmail().getAddress());
        assertNotNull(userSaved.getPassword());
        assertFalse(antonio.getNewPassword().equals(userSaved.getPassword()));
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        assertTrue(passwordEncoder.isPasswordValid(userSaved.getPassword(), antonio.getNewPassword(), "BUDGET-ANALYZER"));
        assertSame(token.entity(), userSaved.getEntity());
    }

    @Test
    public void testCreateTokenNotFound() {
        EditUserView antonio = antonioBanderas();

        expect(tokenManager.findTokenByValue(antonio.getTokenValue())).andReturn(null);
        replay();

        try {
            manager.create(antonio);
            fail();
        } catch (BusinessException error) {
            assertEquals("error", error.getErrorCode());
            assertEquals("token.not.found", error.getErrorMessage());
        }
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

    @Test
    public void testUpdate() {
        EditUserView michelle = michellePfeiffer();
        User updatedUser = new User();

        expect(repository.findByUsername(michelle.getUsername())).andReturn(updatedUser);
        expect(repository.attach(updatedUser)).andReturn(user);
        replay();

        User result = manager.update(michelle);

        assertSame(user, result);
        assertEquals(michelle.getName(), updatedUser.getName());
        assertEquals(michelle.getEmail(), updatedUser.getEmail().getAddress());
        assertNotNull(updatedUser.getPassword());
        assertNotEquals(michelle.getNewPassword(), updatedUser.getPassword());

        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        assertTrue(passwordEncoder.isPasswordValid(updatedUser.getPassword(), michelle.getNewPassword(), "BUDGET-ANALYZER"));
    }

    @Test
    public void testFindById() {
        int id = 1;

        expect(repository.findById(id)).andReturn(user);
        replay();

        User result = manager.findById(id);

        assertSame(user, result);
    }

    @Test
    public void testFindUsersOfEntity() {
        expect(repository.findUsersOfEntity(entity)).andReturn(users);
        replay();

        List<User> results = manager.findUsersOfEntity(entity);

        assertSame(users, results);
    }

    @Test
    public void testTransferAdminRole() {
        int userId = 1;
        // TODO use UserMother
        User newAdminUser = new User();
        User oldAdminUser = new User();
        oldAdminUser.addAuthority(User.ROLE_ADMIN);
        assertTrue(oldAdminUser.isAdmin());

        expect(repository.findById(userId)).andReturn(newAdminUser);
        expect(repository.attach(oldAdminUser)).andReturn(oldAdminUser);
        expect(repository.save(oldAdminUser)).andReturn(oldAdminUser);
        expect(repository.save(newAdminUser)).andReturn(newAdminUser);
        expect(repository.findById(oldAdminUser.getId())).andReturn(oldAdminUser);
        replay();

        User result = manager.transferAdminRole(oldAdminUser, userId);

        assertSame(newAdminUser, result);
        assertTrue(newAdminUser.isAdmin());
        assertFalse(oldAdminUser.isAdmin());
    }
}
