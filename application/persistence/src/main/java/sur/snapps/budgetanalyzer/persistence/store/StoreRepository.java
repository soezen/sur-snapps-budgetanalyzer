package sur.snapps.budgetanalyzer.persistence.store;

import sur.snapps.budgetanalyzer.domain.store.Store;
import sur.snapps.budgetanalyzer.domain.store.StoreLocation;
import sur.snapps.budgetanalyzer.persistence.AbstractRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author sur
 * @since 26/03/2015
 */
public class StoreRepository extends AbstractRepository {

    // TODO add status for store (open, closed, ...)
    // TODO add second dropdown which only has data after store has been selected
    public List<Store> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Store> query = builder.createQuery(Store.class);
        Root<Store> fromStore = query.from(Store.class);
        return entityManager.createQuery(query).getResultList();
    }

    public StoreLocation findLocationById(String id) {
        return entityManager.find(StoreLocation.class, id);
    }
}
