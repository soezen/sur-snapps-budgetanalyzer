package sur.snapps.budgetanalyzer.persistence.transaction;

import sur.snapps.budgetanalyzer.domain.purchase.Purchase;
import sur.snapps.budgetanalyzer.persistence.AbstractRepository;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:53
 */
public class TransactionRepository extends AbstractRepository {

    public Purchase save(Purchase purchase) {
        entityManager.persist(purchase);
        return purchase;
    }
}
