package sur.snapps.budgetanalyzer.business.mail;

import org.springframework.beans.factory.annotation.Value;
import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 20:07
 */
public class MockMailSender implements MailSender {

    @Value("${mail.from_address}")
    private String fromEmail;
    @Value("${mail.from_name}")
    private String fromName;

    @Override
    public void send(TemplateMail mail) {
        String html = mail.template().render();
        System.out.println(html);
    }
}
