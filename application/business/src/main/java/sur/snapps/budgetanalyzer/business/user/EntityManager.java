package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.user.Entity;
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
    public Entity update(EditEntityView entity) {
        Entity managedEntity = entityRepository.findById(entity.getId());
        managedEntity.updateName(entity.getName());
        return managedEntity;
    }

    public Entity findById(String id) {
        return entityRepository.findById(id);
    }

}

// TODO after updating entity name, table header still has old entity name

// TODO jquery or browser or server keeps cache of ajax request, when doing same request twice, update is not done in db

// TODO tab in browser first goes to submit button and only then to cancel
// TODO tab does not enter read only fields