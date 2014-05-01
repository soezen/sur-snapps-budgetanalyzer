package sur.snapps.budgetanalyzer.business.mail;

import com.github.sendgrid.SendGrid;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:46
 */
public abstract class TemplateMailSender {

    protected abstract ST getTemplate();
    protected abstract String getSubject();

    protected static final MailProperties MAIL_PROPERTIES = MailProperties.getInstance();
    private String username = MAIL_PROPERTIES.getSendGridUsername();
    private String password = MAIL_PROPERTIES.getSendGridPassword();
    private String fromEmail = MAIL_PROPERTIES.getFromEmail();
    private String fromName = MAIL_PROPERTIES.getFromName();

    public void sendTo(String toEmail) {
        String html = getTemplate().render();

        SendGrid sendgrid = new SendGrid(username, password);
        SendGridMailSender.newMail()
                .from(fromEmail, fromName)
                .to(toEmail)
                .subject(getSubject())
                .html(html)
                .send(sendgrid);
    }

    protected ST getTemplateFromFile(String templateName) {
        STGroup templateGroup = new STGroupFile("mail/templates.stg");
        return templateGroup.getInstanceOf(templateName);
    }

}
