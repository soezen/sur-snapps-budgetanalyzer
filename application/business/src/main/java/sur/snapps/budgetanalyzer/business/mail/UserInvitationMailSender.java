package sur.snapps.budgetanalyzer.business.mail;

import org.stringtemplate.v4.ST;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:46
 */
public class UserInvitationMailSender extends TemplateMailSender {

    private String token;
    private String inviter;

    private UserInvitationMailSender() { }

    public static UserInvitationMailSender newMail() {
        return new UserInvitationMailSender();
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
        template.add("token", token);
        template.add("inviter", inviter);
        return template;
    }

    @Override
    protected String getSubject() {
        return MailProperties.getInstance().getUserInvitationSubject();
    }
}
