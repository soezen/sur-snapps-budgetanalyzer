package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: SUR
 * Date: 21/04/14
 * Time: 16:32
 */
@Service
public class UserManager {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        user.setEnabled(true);
        user.addAuthority("ROLE_USER");
        return userRepository.save(user);
    }
}
