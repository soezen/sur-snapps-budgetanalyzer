package sur.snapps.budgetanalyzer.business.mail;

import org.springframework.beans.factory.annotation.Value;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:59
 */
public final class MailProperties {

    private static MailProperties instance;


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

    private MailProperties() { }

    public static MailProperties getInstance() {
        if (instance == null) {
            instance = new MailProperties();
        }
        return instance;
    }

    public String getUserInvitationSubject() {
        return userInvitationSubject;
    }

    public String getUserInvitationTemplateName() {
        return userInvitationTemplateName;
    }

    public String getSendGridUsername() {
        return sendGridUsername;
    }

    public String getSendGridPassword() {
        return sendGridPassword;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getFromName() {
        return fromName;
    }
}
