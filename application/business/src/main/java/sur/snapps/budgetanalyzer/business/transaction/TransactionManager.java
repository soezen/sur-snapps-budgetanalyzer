package sur.snapps.budgetanalyzer.business.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.account.AccountManager;
import sur.snapps.budgetanalyzer.business.product.EditProductView;
import sur.snapps.budgetanalyzer.business.product.ProductManager;
import sur.snapps.budgetanalyzer.business.store.StoreManager;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.purchase.Purchase;
import sur.snapps.budgetanalyzer.domain.store.StoreLocation;
import sur.snapps.budgetanalyzer.domain.user.Account;
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
    private AccountManager accountManager;

    @Autowired
    private TransactionRepository transactionRepository;

    // TODO add 2 buttons in product row: 1 to search via code, 1 to search via category & type
    // TODO as long as no store (which uses codes) is selected the button and input of product code is disabled

    // TODO remove checkbox from payment table
    // TODO instead prefill amount with rest to be paid amount
    // TODO when pressing tab and total amount is not reached, add a new row with non-covered amount

    @Transactional
    // TODO turn logging back on when we persist store location and other dependents via webapp and thus also available in history tables
//    @LogEvent(EventType.PURCHASE_CREATED)
    public Purchase create(EditPurchaseView purchaseView) {
        StoreLocation storeLocation = storeManager.findLocationById(purchaseView.getStoreLocationId());

        Purchase.Builder builder = Purchase.newPurchase()
            .date(purchaseView.getDate())
            .store(storeLocation);

        for (EditProductView productView : purchaseView.getProductsFiltered()) {
            Product product = productManager.findById(productView.getId());
            builder = builder.product(product, productView.getAmount(), productView.getUnitPrice());
        }

        for (EditPaymentView paymentView : purchaseView.getPaymentsFiltered()) {
            Account account = accountManager.findById(paymentView.getAccountId());
            builder = builder.payment(account, paymentView.getAmount());
        }

        Purchase purchase = builder.build();
        transactionRepository.save(purchase);
        return purchase;
    }
}
