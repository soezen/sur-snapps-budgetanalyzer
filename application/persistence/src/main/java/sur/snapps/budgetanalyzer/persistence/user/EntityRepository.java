package sur.snapps.budgetanalyzer.persistence.user;

import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.persistence.AbstractRepository;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:24
 */
public class EntityRepository extends AbstractRepository {

    public Entity attach(Entity entity) {
        return entityManager.merge(entity);
    }

    public Entity findById(String entityId) {
        return entityManager.find(Entity.class, entityId);
    }
}
