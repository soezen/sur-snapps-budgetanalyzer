package sur.snapps.budgetanalyzer.business.mail;

import com.github.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;
import sur.snapps.budgetanalyzer.domain.mail.UserInvitationMail;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:59
 */
public class MailFactory {

    @Value("#{systemProperties.SENDGRID_USERNAME}")
    private String sendGridUsername;
    @Value("#{systemProperties.SENDGRID_PASSWORD}")
    private String sendGridPassword;
    @Value("${mail.from_address}")
    private String fromEmail;
    @Value("${mail.from_name}")
    private String fromName;

    @Value("${mail.user_invitation.subject}")
    private String userInvitationSubject;
    @Value("${mail.user_invitation.template}")
    private String userInvitationTemplateName;

    public UserInvitationMail createUserInvitationMail() {
        return UserInvitationMail.newMail(userInvitationTemplateName, userInvitationSubject);
    }

    public void sendMail(TemplateMail<?> mail) {
        String html = mail.template().render();

        SendGrid sendgrid = new SendGrid(sendGridUsername, sendGridPassword);
        SendGridMailSender.newMail(sendgrid).from(fromEmail, fromName)
                .to(mail.to())
                .subject(mail.subject())
                .html(html)
                .send();
    }

}
