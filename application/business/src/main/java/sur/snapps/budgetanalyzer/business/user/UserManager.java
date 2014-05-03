package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenManager tokenManager;

    @Transactional
    public User createUser(User user) {
        if (user.isAdmin()) {
            user.getEntity().setName(user.getName());
        } else {
            Token token = tokenManager.findTokenByValue(user.getTokenValue());
            user.setEntity(token.entity());
            tokenManager.delete(token);
        }
        user.encodePassword();
        user.setEnabled(true);
        user.addAuthority(ROLE_USER);
        return userRepository.save(user);
    }

    public List<User> findUsersOfEntity(Entity entity) {
        System.out.println("ENTITY: " + entity.getId() + " - " + entity.getName());
        return userRepository.findUsersOfEntity(entity);
    }

    public boolean isUsernameUsed(String username) {
        return userRepository.isUsernameUsed(username);
    }

    public User findByUsername(String username) {
        // TODO validate input
        return userRepository.findByUsername(username);
    }
}
