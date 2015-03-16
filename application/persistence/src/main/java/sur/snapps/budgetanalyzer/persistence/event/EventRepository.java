package sur.snapps.budgetanalyzer.persistence.event;

import sur.snapps.budgetanalyzer.domain.event.Event;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:53
 */
public class EventRepository {

    // TODO put in super class
    @PersistenceContext
    private EntityManager entityManager;

    public void flush() {
        entityManager.flush();
    }

    public Event save(Event event) {
        entityManager.persist(event);
        return event;
    }

    public List<Event> findFor(Entity entity, int pageIndex, int pageSize) {
        // TODO-TECH only fetch first X results (or the next page, ...)
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> eventRoot = cq.from(Event.class);

        Subquery<User> userSubQuery = cq.subquery(User.class);
        Root<User> userRoot = userSubQuery.from(User.class);
        userSubQuery.select(userRoot);
        Predicate userCondition = cb.equal(
                userRoot.get("entity"),
                entity
        );
        userSubQuery.where(userCondition);
        cq.where(eventRoot.get("user").in(userSubQuery));

        return entityManager.createQuery(cq)
            .setFirstResult(pageIndex * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
    }
}
