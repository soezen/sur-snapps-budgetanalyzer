package sur.snapps.budgetanalyzer.business.product;

import org.springframework.beans.factory.annotation.Autowired;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.store.StoreProduct;
import sur.snapps.budgetanalyzer.persistence.product.ProductRepository;

/**
 * User: SUR
 * Date: 21/04/14
 * Time: 16:32
 */
public class ProductManager {

    @Autowired
    private ProductRepository productRepository;

    public StoreProduct findByCode(String store, String productCode) {
        return productRepository.findByCode(store, productCode);
    }

    public Product findById(String id) {
        if (id == null) {
            return null;
        }
        return productRepository.findById(id);
    }
}
