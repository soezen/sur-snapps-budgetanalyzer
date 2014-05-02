package sur.snapps.budgetanalyzer.domain.mail;

import org.stringtemplate.v4.ST;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:46
 */
public class UserInvitationMail extends TemplateMail<UserInvitationMail> {

    // TODO relative path needs to be dynamic
    private static final String URL_PATTERN = "http://%1$s:%2$s%3$s/userRegistrationWithToken?token=%4$s";

    private String token;
    private String inviter;
    private String host;
    private int port;
    private String context;

    private UserInvitationMail(String template, String subject) {
        super(template, subject);
    }


    public static UserInvitationMail newMail(String template, String subject) {
        return new UserInvitationMail(template, subject);
    }

    @Override
    public UserInvitationMail to(String toEmail) {
        this.toEmail = toEmail;
        return this;
    }

    public UserInvitationMail context(String context) {
        this.context = context;
        return this;
    }

    public UserInvitationMail port(int port) {
        this.port = port;
        return this;
    }

    public UserInvitationMail host(String host) {
        this.host = host;
        return this;
    }

    public UserInvitationMail token(String token) {
        this.token = token;
        return this;
    }

    public UserInvitationMail inviter(String inviter) {
        this.inviter = inviter;
        return this;
    }

    @Override
    protected ST getTemplate(String name) {
        ST template = getTemplateFromFile(name);
        template.add("url", String.format(URL_PATTERN, host, port, context, token));
        template.add("inviter", inviter);
        return template;
    }
}
