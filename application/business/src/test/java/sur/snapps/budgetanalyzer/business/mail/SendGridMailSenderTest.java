package sur.snapps.budgetanalyzer.business.mail;

import com.github.sendgrid.SendGrid;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.stringtemplate.v4.ST;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectInto;
import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;

import static org.easymock.EasyMock.expect;
import static org.unitils.easymock.EasyMockUnitils.replay;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 18:02
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class SendGridMailSenderTest {

    private SendGridMailSender mailSender;

    @Mock
    private SendGrid sendGrid;

    @InjectInto(target = "mailSender", property = "fromEmail")
    private String fromEmail = "fromEmail";
    @InjectInto(target = "mailSender", property = "fromName")
    private String fromName = "fromName";

    @Mock
    private TemplateMail mail;
    @Mock
    private ST template;

    @Before
    public void init() {
        mailSender = new SendGridMailSender(sendGrid);
    }

    @Test
    public void testNoArgConstructor() {
        mailSender = new SendGridMailSender();
    }

    @Test
    public void testSend() {
        expect(mail.to()).andReturn("toEmail");
        expect(sendGrid.addTo("toEmail")).andReturn(sendGrid);
        expect(sendGrid.setFrom(fromEmail)).andReturn(sendGrid);
        expect(sendGrid.setFromName(fromName)).andReturn(sendGrid);
        expect(mail.subject()).andReturn("subject");
        expect(sendGrid.setSubject("subject")).andReturn(sendGrid);
        expect(mail.template()).andReturn(template);
        expect(template.render()).andReturn("html");
        expect(sendGrid.setHtml("html")).andReturn(sendGrid);
        expect(sendGrid.send()).andReturn("{\"message\": \"success\"}");
        replay();

        mailSender.send(mail);
    }
}
