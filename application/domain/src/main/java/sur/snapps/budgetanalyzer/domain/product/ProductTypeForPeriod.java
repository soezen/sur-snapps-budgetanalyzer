package sur.snapps.budgetanalyzer.domain.product;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sur
 * @since 03/04/2015
 */
public class ProductTypeForPeriod {

    private ProductType productType;
    private Double totalAmount;

    public ProductTypeForPeriod(ProductType productType, double totalAmount) {
        this.totalAmount = totalAmount;
        this.productType = productType;
    }

    public double totalAmount() {
        return totalAmount == null ? 0.0 : totalAmount;
    }

    public String id() {
        return productType.getId();
    }

    public String name() {
        return productType.name();
    }

    /**
     * @param categoryId
     * @return true when any parent category has given id
     */
    public boolean hasAnyParentCategory(String categoryId) {
        Category parentCategory = productType.category();
        while (parentCategory != null) {
            if (parentCategory.getId().equals(categoryId)) {
                return true;
            }
            parentCategory = parentCategory.parent();
        }
        return false;
    }

    public boolean hasDirectParentCategory(String categoryId) {
        return productType.category().getId().equals(categoryId);
    }

    /**
     * @param categoryId
     * @return list of categories that are sub categories of the given category and in which this product belongs to
     */
    public List<Category> subCategoriesOf(String categoryId) {
        LinkedList<Category> categories = Lists.newLinkedList();
        Category parentCategory = productType.category();
        while (parentCategory != null) {
            if (parentCategory.getId().equals(categoryId)) {
                break;
            }
            categories.addFirst(parentCategory);
            parentCategory = parentCategory.parent();
        }
        return categories;
    }
}
