package sur.snapps.budgetanalyzer.business.product;

/**
 * @author sur
 * @since 22/03/2015
 */
public class EditCategoryView {

    private String id;
    private String parent;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
