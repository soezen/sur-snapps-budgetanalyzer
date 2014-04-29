package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.mail.UserInvitationMailSender;
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

    @Transactional
    public void createToken(Entity entity, String mail, String inviter) {
        Token token = tokenRepository.save(Token.createUserInvitationToken().generateToken().entity(entity).build());

        // TODO handle exceptions
        UserInvitationMailSender.newMail()
                .token(token.value())
                .inviter(inviter)
                .sendTo(mail);

        // TODO add updated tms in db
        // TODO add version in db


    }

}
