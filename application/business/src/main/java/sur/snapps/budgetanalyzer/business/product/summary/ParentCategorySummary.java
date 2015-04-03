package sur.snapps.budgetanalyzer.business.product.summary;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import sur.snapps.budgetanalyzer.domain.product.Category;
import sur.snapps.budgetanalyzer.domain.product.ProductTypeForPeriod;

import java.util.List;

import static com.google.common.base.Objects.equal;

/**
 * @author sur
 * @since 03/04/2015
 */
public class ParentCategorySummary extends CategorySummary {

    private List<CategorySummary> children;

    public ParentCategorySummary(String id, String name) {
        super(id, name);
        children = Lists.newArrayList();
    }

    public List<CategorySummary> getChildren() {
        return children;
    }

    public void setChildren(List<CategorySummary> children) {
        this.children = children;
    }

    @Override
    public boolean add(ProductTypeForPeriod productType) {
        if (!isTopLevelSummary()
            && !productType.hasAnyParentCategory(getId())) {
            // product does not belong in this category
            return false;
        }
        if (productType.hasDirectParentCategory(getId())) {
            // this is the category the product belongs in
            ChildCategorySummary child = new ChildCategorySummary(productType.id(), productType.name(), productType.totalAmount());
            children.add(child);
            return true;
        }

        // this is not direct parent of product, see if any of the children can add the product
        for (CategorySummary summary : children) {
            if (summary.add(productType)) {
                return true;
            }
        }

        // no child of mine was able to add the product, create the necessary category
        List<Category> categories = productType.subCategoriesOf(getId());
        ParentCategorySummary parentSummary = this;
        for (Category category : categories) {
            ParentCategorySummary summary = new ParentCategorySummary(category.getId(), category.name());
            parentSummary.getChildren().add(summary);
            parentSummary = summary;
        }
        ChildCategorySummary child = new ChildCategorySummary(productType.id(), productType.name(), productType.totalAmount());
        parentSummary.getChildren().add(child);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParentCategorySummary) {
            ParentCategorySummary other = (ParentCategorySummary) obj;
            return equal(getId(), other.getId())
                && equal(getName(), other.getName())
                && equal(children, other.children);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), children);
    }
}
