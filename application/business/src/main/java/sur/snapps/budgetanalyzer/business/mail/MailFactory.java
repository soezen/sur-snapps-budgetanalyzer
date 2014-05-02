package sur.snapps.budgetanalyzer.business.mail;

import org.springframework.beans.factory.annotation.Value;
import sur.snapps.budgetanalyzer.domain.mail.UserInvitationMail;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:59
 */
public class MailFactory {

    @Value("${mail.user_invitation.subject}")
    private String userInvitationSubject;
    @Value("${mail.user_invitation.template}")
    private String userInvitationTemplateName;

    public UserInvitationMail createUserInvitationMail() {
        return UserInvitationMail.newMail(userInvitationTemplateName, userInvitationSubject);
    }

}
