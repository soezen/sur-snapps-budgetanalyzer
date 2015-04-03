package sur.snapps.budgetanalyzer.business.product;

import org.springframework.beans.factory.annotation.Autowired;
import sur.snapps.budgetanalyzer.business.product.summary.CategorySummary;
import sur.snapps.budgetanalyzer.business.product.summary.ParentCategorySummary;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.product.ProductType;
import sur.snapps.budgetanalyzer.domain.store.StoreProduct;
import sur.snapps.budgetanalyzer.persistence.product.ProductRepository;

import java.util.List;

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

    public CategorySummary generateCategorySummary() {
        ParentCategorySummary summary = CategorySummary.newSummary("Category Summary for month X");
        List<ProductType> productTypes = productRepository.findProductTypes();
        for (ProductType productType : productTypes) {
            summary.add(productType);
        }
        return summary;
    }

}
