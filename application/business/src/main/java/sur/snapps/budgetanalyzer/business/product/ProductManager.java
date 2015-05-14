package sur.snapps.budgetanalyzer.business.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.product.summary.CategorySummary;
import sur.snapps.budgetanalyzer.business.product.summary.ParentCategorySummary;
import sur.snapps.budgetanalyzer.domain.product.Category;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.product.ProductType;
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

    // TODO add legend to chart and allow user to select and deselect categories to include

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Category create(EditCategoryView categoryView) {
        Category parentCategory = findCategoryById(categoryView.getParent());
        Category category = (parentCategory != null
            ? parentCategory.createSubCategory()
            : Category.newCategory())
            .name(categoryView.getName())
            .build();

        return productRepository.save(category);
    }

    public StoreProduct findByCode(String store, String productCode) {
        return productRepository.findByCode(store, productCode);
    }

    public List<ProductType> findProductTypes(String categoryId) {
        return productRepository.findProductTypes(categoryId);
    }

    public List<Product> findProductsOfType(String productTypeId) {
        return productRepository.findProductsOfType(productTypeId);
    }

    public Product findById(String id) {
        if (id == null) {
            return null;
        }
        return productRepository.findById(id);
    }

    public Category findCategoryById(String id) {
        if (id == null) {
            return null;
        }
        return productRepository.findCategoryById(id);
    }

    public List<Category> findCategoriesWithParent(Category parent) {
        return productRepository.findCategoriesWithParent(parent);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
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
