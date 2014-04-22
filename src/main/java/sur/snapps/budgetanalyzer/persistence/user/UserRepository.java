package sur.snapps.budgetanalyzer.persistence.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.domain.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:24
 */
@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
}
