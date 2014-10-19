package sur.snapps.budgetanalyzer.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * User: SUR
 * Date: 19/10/14
 * Time: 17:30
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    // TODO-TECH also use uid as generated keys? see mail (gmail) sent on 17/06/2014

    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getDisplayValue();
}
