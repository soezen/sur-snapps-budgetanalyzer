package sur.snapps.budgetanalyzer.business.user;

import sur.snapps.budgetanalyzer.domain.user.Entity;

import java.io.Serializable;

/**
 * User: SUR
 * Date: 27/06/14
 * Time: 13:15
 */
public class EditEntityView implements Serializable {

    private int id;
    private String name;

    public EditEntityView() { }

    public EditEntityView(Entity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
