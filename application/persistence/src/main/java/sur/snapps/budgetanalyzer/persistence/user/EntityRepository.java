package sur.snapps.budgetanalyzer.persistence.user;

import sur.snapps.budgetanalyzer.domain.user.Entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:24
 */
public class EntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Entity attach(Entity entity) {
        return entityManager.merge(entity);
    }

    public Entity findById(int entityId) {
        return entityManager.find(Entity.class, entityId);
    }
}
