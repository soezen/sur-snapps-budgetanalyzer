package sur.snapps.budgetanalyzer.business.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.business.product.EditProductView;
import sur.snapps.budgetanalyzer.business.product.ProductManager;
import sur.snapps.budgetanalyzer.business.store.StoreManager;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.purchase.Purchase;
import sur.snapps.budgetanalyzer.domain.store.StoreLocation;
import sur.snapps.budgetanalyzer.persistence.transaction.TransactionRepository;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:51
 */
public class TransactionManager {

    @Autowired
    private StoreManager storeManager;
    @Autowired
    private ProductManager productManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @LogEvent(EventType.PURCHASE_CREATED)
    public Purchase create(EditPurchaseView purchaseView) {
        StoreLocation storeLocation = storeManager.findLocationById(purchaseView.getStoreLocationId());

        Purchase.Builder builder = Purchase.newPurchase()
            .date(purchaseView.getDate())
            .store(storeLocation);

        for (EditProductView productView : purchaseView.getProductsFiltered()) {
            Product product = productManager.findById(productView.getId());
            builder = builder.product(product, productView.getAmount(), productView.getUnitPrice());
        }
        // TODO add payments
        Purchase purchase = builder.build();
        transactionRepository.save(purchase);
        return purchase;
    }
}
