package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;

/**
 * User: SUR
 * Date: 21/04/14
 * Time: 16:32
 */
public class UserManager {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        Entity entity = new Entity();
        entity.setName(user.getUsername());
        entity.setOwned(true);
        entity.setShared(false);

        user.encodePassword();
        user.setEnabled(true);
        user.setEntity(entity);
        user.setAdmin(true);
        user.addAuthority("ROLE_USER");
        return userRepository.save(user);
    }

    public boolean isUsernameUsed(String username) {
        return userRepository.isUsernameUsed(username);
    }

    public User findByUsername(String username) {
        // TODO validate input
        return userRepository.findByUsername(username);
    }
}