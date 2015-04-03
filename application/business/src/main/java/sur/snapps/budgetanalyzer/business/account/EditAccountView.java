package sur.snapps.budgetanalyzer.business.account;

import sur.snapps.budgetanalyzer.domain.user.AccountType;

import java.io.Serializable;

/**
 * @author sur
 * @since 16/03/2015
 */
public class EditAccountView implements Serializable {

    private String name;
    private AccountType type;
    private String ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}