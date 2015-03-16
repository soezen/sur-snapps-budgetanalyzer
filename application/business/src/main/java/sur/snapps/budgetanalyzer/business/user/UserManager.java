package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.business.exception.BusinessException;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;

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
        User.Builder userBuilder;
        String tokenValue = inputUser.getTokenValue();
        if (tokenValue == null) {
            // TODO see if it is possible to change text from 'X joined entity X' to 'new user and entity X created'
            // for this the methods will have to be separated in manager and thus the if statement will have to happen in the front-end
            userBuilder = User.createAdmin()
                .entity(Entity.newOwnedEntity().name(inputUser.getName()).build());
        } else {
            Token token = tokenManager.findTokenByValue(tokenValue);
            if (token == null) {
                throw new BusinessException("error", "token.not.found");
            }
            userBuilder = User.createUser()
                .entity(token.entity());
            tokenManager.delete(token);
        }
        User user = userBuilder
            .username(inputUser.getUsername())
            .email(inputUser.getEmail())
            .name(inputUser.getName())
            .password(inputUser.getNewPassword())
            .build();

        return userRepository.save(user);
    }

    @Transactional
    @LogEvent(EventType.USER_UPDATE)
    public User update(EditUserView user) {
        User managedUser = userRepository.findByUsername(user.getUsername());
        managedUser.updateEmail(user.getEmail());
        managedUser.updateName(user.getName());
        managedUser.updatePassword(user.getNewPassword());
        return managedUser;
    }

    public User findById(String userId) {
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

    // TODO-FUNC UC-1 pagination for dashboard events, only fetch first X events from db
    // TODO-FUNC UC-1 confirmation popup when transferring admin role

    @Transactional
    @LogEvent(EventType.ADMIN_TRANSFER)
    public User transferAdminRole(User currentAdminUser, String userId) {
        User newAdminUser = userRepository.findById(userId);
        currentAdminUser = userRepository.attach(currentAdminUser);
        currentAdminUser.transferAdminRoleTo(newAdminUser);
        return newAdminUser;
    }
}
