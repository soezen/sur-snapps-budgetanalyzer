package sur.snapps.budgetanalyzer.business.product;

import org.springframework.beans.factory.annotation.Autowired;
import sur.snapps.budgetanalyzer.business.product.summary.CategorySummary;
import sur.snapps.budgetanalyzer.business.product.summary.ParentCategorySummary;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.product.ProductTypeForPeriod;
import sur.snapps.budgetanalyzer.domain.store.StoreProduct;
import sur.snapps.budgetanalyzer.persistence.product.ProductRepository;

import java.util.Date;
import java.util.List;

import static sur.snapps.budgetanalyzer.util.DateUtil.firstDayOfMonth;
import static sur.snapps.budgetanalyzer.util.DateUtil.lastDayOfMonth;

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

    public CategorySummary generateCategorySummary(Date date) {
        ParentCategorySummary summary = CategorySummary.newSummary("Category Summary for month X");
        List<ProductTypeForPeriod> productTypes = productRepository.findProductTypesForPeriod(firstDayOfMonth(date), lastDayOfMonth(date));
        for (ProductTypeForPeriod productType : productTypes) {
            summary.add(productType);
        }
        return summary;
    }

}
