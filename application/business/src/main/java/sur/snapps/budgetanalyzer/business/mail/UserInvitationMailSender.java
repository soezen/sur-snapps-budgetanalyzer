package sur.snapps.budgetanalyzer.business.mail;

import org.stringtemplate.v4.ST;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:46
 */
public class UserInvitationMailSender extends TemplateMailSender {

    // TODO relative path needs to be dynamic
    private static final String URL_PATTERN = "http://%1$s:%2$s%3$s/userRegistrationWithToken?token=%4$s";

    private String token;
    private String inviter;
    private String host;
    private int port;
    private String context;

    private UserInvitationMailSender() { }

    public static UserInvitationMailSender newMail() {
        return new UserInvitationMailSender();
    }

    public UserInvitationMailSender context(String context) {
        this.context = context;
        return this;
    }

    public UserInvitationMailSender port(int port) {
        this.port = port;
        return this;
    }

    public UserInvitationMailSender host(String host) {
        this.host = host;
        return this;
    }

    public UserInvitationMailSender token(String token) {
        this.token = token;
        return this;
    }

    public UserInvitationMailSender inviter(String inviter) {
        this.inviter = inviter;
        return this;
    }

    @Override
    protected ST getTemplate() {
        MailProperties mailProperties = MailProperties.getInstance();
        ST template = getTemplateFromFile(mailProperties.getUserInvitationTemplateName());
        template.add("url", String.format(URL_PATTERN, host, port, context, token));
        template.add("inviter", inviter);
        return template;
    }

    @Override
    protected String getSubject() {
        return MailProperties.getInstance().getUserInvitationSubject();
    }
}
