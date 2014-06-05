package sur.snapps.budgetanalyzer.tests.dummy;

import sur.snapps.jetta.database.dummy.Dummy;
import sur.snapps.jetta.database.dummy.Identifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: SUR
 * Date: 5/06/14
 * Time: 7:01
 */
@Dummy("tokens/token")
public class DummyToken {

    private int id;
    @Identifier
    private String value;
    private int entityId;
    private String email;
    private Date expirationDate;
    private String status;
    private String type;

    public int id() {
        return id;
    }

    public String value() {
        return value;
    }

    public String expirationDate() {
        if (expirationDate != null) {
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            format.setLenient(false);
            return format.format(expirationDate);
        }
        return null;
    }

    public int nbrOfLinks() {
        if (status.equals("VALID")) {
            return 3;
        }
        return 1;
    }

    public String status() {
        return status;
    }

    public String email() {
        return email;
    }

    public String type() {
        return type;
    }

    public int entityId() {
        return entityId;
    }
}
