package sur.snapps.budgetanalyzer.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailTest {

    @Test
    public void create() {
        String address = "hannibal@a-team.com";
        Email emailOfHannibal = Email.create(address);
        assertEquals(address, emailOfHannibal.address());
    }
}