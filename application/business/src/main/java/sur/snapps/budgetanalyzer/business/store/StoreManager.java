package sur.snapps.budgetanalyzer.business.store;

import org.springframework.beans.factory.annotation.Autowired;
import sur.snapps.budgetanalyzer.domain.store.Store;
import sur.snapps.budgetanalyzer.domain.store.StoreLocation;
import sur.snapps.budgetanalyzer.persistence.store.StoreRepository;

import java.util.List;

/**
 * @author sur
 * @since 26/03/2015
 */
public class StoreManager {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public StoreLocation findLocationById(String id) {
        if (id == null) {
            return null;
        }
        return storeRepository.findLocationById(id);
    }
}
