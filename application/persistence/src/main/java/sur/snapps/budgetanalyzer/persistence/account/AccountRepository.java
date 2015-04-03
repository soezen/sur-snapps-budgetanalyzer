package sur.snapps.budgetanalyzer.persistence.account;

import sur.snapps.budgetanalyzer.domain.user.Account;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.AbstractRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:24
 */
public class AccountRepository extends AbstractRepository {

    // TODO show number of results in table

    public Account save(Account account) {
        entityManager.persist(account);
        return account;
    }

    // TODO test this class
    public List<Account> findFor(String entityId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = builder.createQuery(Account.class);

        Root<Account> fromAccounts = query.from(Account.class);
        Join<Account, User> joinUsers = fromAccounts.join("owner");
        Join<User, Entity> joinEntities = joinUsers.join("entity");
        Predicate idCondition = builder.equal(joinEntities.get("id"), entityId);
        query.where(idCondition);

        return entityManager.createQuery(query).getResultList();
    }

    public Account findById(String id) {
        return entityManager.find(Account.class, id);
    }
}
