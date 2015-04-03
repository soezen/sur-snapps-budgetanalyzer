package sur.snapps.budgetanalyzer.business.product.summary;

import sur.snapps.budgetanalyzer.domain.product.ProductTypeForPeriod;

/**
 * @author sur
 * @since 03/04/2015
 */
public abstract class CategorySummary {

    public static final String TOP_LEVEL = "SUMMARY";

    private String id;
    private String name;

    public CategorySummary(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected abstract boolean add(ProductTypeForPeriod productType);

    public static ParentCategorySummary newSummary(String name) {
        return new ParentCategorySummary(TOP_LEVEL, name);
    }

    public boolean isTopLevelSummary() {
        return id.equals(TOP_LEVEL);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
