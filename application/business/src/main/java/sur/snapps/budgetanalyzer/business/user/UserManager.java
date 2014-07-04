package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.user.Email;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;

import java.util.List;

/**
 * User: SUR
 * Date: 21/04/14
 * Time: 16:32
 */
public class UserManager {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenManager tokenManager;

    @Transactional
    @LogEvent(EventType.USER_REGISTRATION)
    public User create(EditUserView inputUser) {
        User user = new User();
        user.setUsername(inputUser.getUsername());
        user.setEmail(new Email(inputUser.getEmail()));
        String name = inputUser.getName();
        user.setName(name);
        user.setPassword(inputUser.getNewPassword());
        user.encodePassword();
        user.setEnabled(true);
        user.addAuthority(User.ROLE_USER);

        String tokenValue = inputUser.getTokenValue();

        if (tokenValue == null) {
            user.setEntity(Entity.newOwnedEntity().name(name).build());
            user.addAuthority(User.ROLE_ADMIN);
        } else {
            Token token = tokenManager.findTokenByValue(tokenValue);
            if (token == null) {
                throw new BusinessException("error", "token.not.found");
            }
            user.setEntity(token.entity());
            tokenManager.delete(token);
        }
        return userRepository.save(user);
    }

    @Transactional
    // TODO try to get this to work
//    @LogEvent(EventType.USER_UPDATE)
    public User update(EditUserView user) {
        System.out.println(user.getUsername());
        User managedUser = userRepository.findByUsername(user.getUsername());
        managedUser.setEmail(new Email(user.getEmail()));
        managedUser.setName(user.getName());
        if (user.getNewPassword() != null) {
            managedUser.setPassword(user.getNewPassword());
            managedUser.encodePassword();
        }
        return userRepository.attach(managedUser);
    }

    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    public List<User> findUsersOfEntity(Entity entity) {
        return userRepository.findUsersOfEntity(entity);
    }

    public boolean isUsernameUsed(String username) {
        return userRepository.isUsernameUsed(username);
    }

    public User findByUsername(String username) {
        // TODO-FUNC UC-1 validate input
        return userRepository.findByUsername(username);
    }

    @Transactional
    @LogEvent(EventType.ADMIN_TRANSFER)
    public User transferAdminRole(User currentAdminUser, int userId) {
        User newAdminUser = userRepository.findById(userId);
        userRepository.attach(currentAdminUser);
        newAdminUser.addAuthority(User.ROLE_ADMIN);
        currentAdminUser.removeAuthority(User.ROLE_ADMIN);
        userRepository.save(currentAdminUser);
        return userRepository.save(newAdminUser);
    }
}
