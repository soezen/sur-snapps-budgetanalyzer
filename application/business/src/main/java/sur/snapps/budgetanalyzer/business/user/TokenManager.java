package sur.snapps.budgetanalyzer.business.user;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    private MailService mailService;

    @Transactional
    public void createToken(Entity entity, String mail, String inviter) {
        Token token = new Token();
        token.generateToken();
        token.setEntity(entity);
        token.setExpirationDate(DateTime.now().plusDays(7));
        tokenRepository.save(token);

        mailService.sendUserInvitationMail(token, mail, inviter);

        // TODO how to delete the expired tokens?
        // TODO add updated tms in db
        // TODO add version in db


    }

}
