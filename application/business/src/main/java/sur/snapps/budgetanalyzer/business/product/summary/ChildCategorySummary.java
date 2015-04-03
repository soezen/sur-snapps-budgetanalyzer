package sur.snapps.budgetanalyzer.business.product.summary;

import com.google.common.base.Objects;
import sur.snapps.budgetanalyzer.domain.product.ProductType;

import static com.google.common.base.Objects.equal;

/**
 * @author sur
 * @since 03/04/2015
 */
public class ChildCategorySummary extends CategorySummary {

    private int size;

    public ChildCategorySummary(String id, String name, int size) {
        super(id, name);
        this.size = size;
    }

    @Override
    protected boolean add(ProductType productType) {
        return false;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChildCategorySummary) {
            ChildCategorySummary other = (ChildCategorySummary) obj;
            return equal(getId(), other.getId())
                && equal(getName(), other.getName())
                && equal(size, other.size);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), size);
    }
}
