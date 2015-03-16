package sur.snapps.budgetanalyzer.business.account;

import java.io.Serializable;

/**
 * @author sur
 * @since 16/03/2015
 */
public class EditAccountView implements Serializable {

    private String name;
    private String ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}