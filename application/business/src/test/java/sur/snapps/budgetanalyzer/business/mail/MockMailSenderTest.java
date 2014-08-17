package sur.snapps.budgetanalyzer.business.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.stringtemplate.v4.ST;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.TestedObject;
import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;

import static org.easymock.EasyMock.expect;
import static org.unitils.easymock.EasyMockUnitils.replay;

/**
 * User: SUR
 * Date: 17/08/14
 * Time: 14:26
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class MockMailSenderTest {

    @TestedObject
    private MockMailSender sender;

    @Mock
    private TemplateMail mail;
    @Mock
    private ST template;

    @Test
    public void testSend() {
        expect(mail.template()).andReturn(template);
        expect(template.render()).andReturn("mail content");
        replay();

        sender.send(mail);
    }
}
