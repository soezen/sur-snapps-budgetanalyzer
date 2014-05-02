package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.mail.MailFactory;
import sur.snapps.budgetanalyzer.domain.mail.Url;
import sur.snapps.budgetanalyzer.domain.mail.UserInvitationMail;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.persistence.user.TokenRepository;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:51
 */
public class TokenManager {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MailFactory mailFactory;

    @Transactional
    public void createToken(Entity entity, String mail, String inviter, Url url) {
        Token token = tokenRepository.save(Token.createUserInvitationToken().generateToken().entity(entity).build());

        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(inviter)
                .to(mail);
        mailFactory.sendMail(userInvitationMail);

        // TODO add updated tms in db
        // TODO add version in db


    }

}
