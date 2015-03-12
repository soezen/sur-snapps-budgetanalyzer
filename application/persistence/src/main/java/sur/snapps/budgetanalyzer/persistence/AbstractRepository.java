package sur.snapps.budgetanalyzer.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author sur
 * @since 10/03/2015
 */
public class AbstractRepository {

    @PersistenceContext
    protected EntityManager entityManager;
}
