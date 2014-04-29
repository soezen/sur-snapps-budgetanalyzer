package sur.snapps.budgetanalyzer.business.mail;

import com.github.sendgrid.SendGrid;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 20:07
 */
public class SendGridMailSender {

    private String fromEmail;
    private String fromName;
    private String toEmail;
    private String subject;
    private String html;

    private SendGridMailSender() { }

    public void send(SendGrid sendGrid) {
        sendGrid.addTo(toEmail);
        sendGrid.setFrom(fromEmail);
        sendGrid.setFromName(fromName);
        sendGrid.setSubject(subject);
        sendGrid.setHtml(html);
        sendGrid.send();
    }

    public static SendGridMailSender newMail() {
        return new SendGridMailSender();
    }

    public SendGridMailSender from(String email, String name) {
        fromEmail = email;
        fromName = name;
        return this;
    }

    public SendGridMailSender to(String email) {
        toEmail = email;
        return this;
    }

    public SendGridMailSender subject(String subject) {
        this.subject = subject;
        return this;
    }

    public SendGridMailSender html(String html) {
        this.html = html;
        return this;
    }
}
