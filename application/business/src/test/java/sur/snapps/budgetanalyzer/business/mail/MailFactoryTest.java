package sur.snapps.budgetanalyzer.business.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import sur.snapps.budgetanalyzer.domain.mail.UserInvitationMail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 17:31
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class MailFactoryTest {

    @TestedObject
    private MailFactory factory;

    @InjectInto(property = "userInvitationSubject")
    private String userInvitationSubject = "subject";

    @Test
    public void testCreateUserInvitationMail() {
        UserInvitationMail mail = factory.createUserInvitationMail();

        assertNotNull(mail);
        assertEquals(userInvitationSubject, mail.subject());
    }
}
