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
import sur.snapps.budgetanalyzer.business.account.AccountManager;
import sur.snapps.budgetanalyzer.business.exception.BusinessException;
import sur.snapps.budgetanalyzer.domain.user.Account;
import sur.snapps.budgetanalyzer.domain.user.AccountType;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;

import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

    @Mock
    @InjectIntoByType
    private AccountManager accountManager;

    @Dummy
    private User user;
    @Dummy
    private List<User> users;
    @Dummy
    private Entity entity;
    @Dummy
    private Account account;

    @Test
    public void testCreateUserAdmin() {
        Capture<User> userCapture = new Capture<>();
        Capture<Account> accountCapture = new Capture<>();
        EditUserView michelle = michellePfeiffer();

        expect(accountManager.save(capture(accountCapture))).andReturn(account);
        expect(repository.save(capture(userCapture))).andReturn(user);
        replay();

        User result = manager.create(michelle);

        assertNotNull(result);
        User userSaved = userCapture.getValue();
        assertNotNull(userSaved);
        assertEquals(michelle.getUsername(), userSaved.username());
        assertEquals(michelle.getName(), userSaved.name());
        assertEquals(michelle.getEmail(), userSaved.email().address());
        assertNotNull(userSaved.encryptedPassword());
        assertFalse(michelle.getNewPassword().equals(userSaved.encryptedPassword()));
        // TODO move spring security dependency out of domain and into business
        // this means that the encryption of password is done in the business layer
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        // TODO put in util class
        assertTrue(passwordEncoder.isPasswordValid(userSaved.encryptedPassword(), michelle.getNewPassword(), "BUDGET-ANALYZER"));

        Entity entity = userSaved.entity();
        assertEquals(michelle.getName(), entity.getName());
        assertTrue(entity.isOwned());
        assertFalse(entity.isShared());

        Account actualAccount = accountCapture.getValue();
        assertNotNull(actualAccount);
        assertEquals(AccountType.CASH, actualAccount.type());
        assertSame(user, actualAccount.owner());
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
        assertEquals(antonio.getUsername(), userSaved.username());
        assertEquals(antonio.getName(), userSaved.name());
        assertEquals(antonio.getEmail(), userSaved.email().address());
        assertNotNull(userSaved.encryptedPassword());
        assertFalse(antonio.getNewPassword().equals(userSaved.encryptedPassword()));
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        assertTrue(passwordEncoder.isPasswordValid(userSaved.encryptedPassword(), antonio.getNewPassword(), "BUDGET-ANALYZER"));
        assertSame(token.entity(), userSaved.entity());
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
        User updatedUser = User.createUser().build();

        expect(repository.findByUsername(michelle.getUsername())).andReturn(updatedUser);
        replay();

        User result = manager.update(michelle);

        assertSame(updatedUser, result);
        assertEquals(michelle.getName(), updatedUser.name());
        assertEquals(michelle.getEmail(), updatedUser.email().address());
        assertNotNull(updatedUser.encryptedPassword());
        assertNotEquals(michelle.getNewPassword(), updatedUser.encryptedPassword());

        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        assertTrue(passwordEncoder.isPasswordValid(updatedUser.encryptedPassword(), michelle.getNewPassword(), "BUDGET-ANALYZER"));
    }

    @Test
    public void testFindById() {
        String id = "1";

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
        String userId = "1";
        // TODO use UserMother
        User newAdminUser = User.createUser().build();
        User oldAdminUser = User.createAdmin().build();
        assertTrue(oldAdminUser.isAdmin());

        expect(repository.findById(userId)).andReturn(newAdminUser);
        expect(repository.attach(oldAdminUser)).andReturn(oldAdminUser);
        replay();

        User result = manager.transferAdminRole(oldAdminUser, userId);

        assertSame(newAdminUser, result);
        assertTrue(newAdminUser.isAdmin());
        assertFalse(oldAdminUser.isAdmin());
    }
}
