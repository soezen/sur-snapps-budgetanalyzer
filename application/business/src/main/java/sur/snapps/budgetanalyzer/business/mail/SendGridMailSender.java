package sur.snapps.budgetanalyzer.business.mail;

import com.github.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 20:07
 */
public class SendGridMailSender {

    @Value("${mail.from_address}")
    private String fromEmail;
    @Value("${mail.from_name}")
    private String fromName;

    private SendGrid sendGrid;

    public SendGridMailSender() {}

    public SendGridMailSender(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void send(TemplateMail mail) {
        String result = sendGrid.addTo(mail.to())
                .setFrom(fromEmail)
                .setFromName(fromName)
                .setSubject(mail.subject())
                .setHtml(mail.template().render())
                .send();
        System.out.println(result);
    }
}
