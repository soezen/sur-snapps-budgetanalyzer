package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.EntityRepository;

/**
 * User: SUR
 * Date: 21/04/14
 * Time: 16:32
 */
public class EntityManager {

    @Autowired
    private EntityRepository entityRepository;

    @Transactional
    @LogEvent(EventType.ENTITY_UPDATE)
    // TODO remove user argument
    public Entity update(User user, EditEntityView entity) {
        Entity managedEntity = entityRepository.findById(entity.getId());
        managedEntity.updateName(entity.getName());
        return entityRepository.attach(managedEntity);
    }

    public Entity findById(String id) {
        return entityRepository.findById(id);
    }
}
