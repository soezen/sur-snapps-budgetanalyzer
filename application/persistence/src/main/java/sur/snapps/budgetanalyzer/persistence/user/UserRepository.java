package sur.snapps.budgetanalyzer.persistence.user;

import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.AbstractRepository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:24
 */
public class UserRepository extends AbstractRepository {

    public User attach(User user) {
        return entityManager.merge(user);
    }

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public void flush() {
        entityManager.flush();
    }

    public List<User> findUsersOfEntity(Entity entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Predicate condition = builder.equal(query.from(User.class).get("entity"), entity);
        query.where(condition);
        return entityManager.createQuery(query).getResultList();
    }

    public User findById(String userId) {
        return entityManager.find(User.class, userId);
    }

    public User findByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Predicate condition = builder.equal(
                // TODO-TECH use User_ class instead
                query.from(User.class).get("username"),
                username);
        query.where(condition);

        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }
        return null;
    }

    public boolean isUsernameUsed(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<User> userRoot = query.from(User.class);
        Expression<Long> count = builder.count(userRoot);
        query.select(count).where(builder.equal(userRoot.get("username"), username));

        return entityManager.createQuery(query).getSingleResult() > 0;
    }
}
