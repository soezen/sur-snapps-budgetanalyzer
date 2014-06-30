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

    @PersistenceContext
    private EntityManager entityManager;

    public Event save(Event event) {
        event = entityManager.merge(event);
        entityManager.persist(event);
        return event;
    }

    public List<Event> getEvents(Entity entity) {
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

        return entityManager.createQuery(cq).getResultList();
    }
}
