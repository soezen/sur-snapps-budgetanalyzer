package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.mail.MailFactory;
import sur.snapps.budgetanalyzer.business.mail.MailSender;
import sur.snapps.budgetanalyzer.domain.mail.Url;
import sur.snapps.budgetanalyzer.domain.mail.UserInvitationMail;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.TokenRepository;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;

import java.util.List;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:51
 */
public class TokenManager {

    @Autowired
    private UserManager userManager;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MailFactory mailFactory;

    @Autowired
    private MailSender mailSender;

    public Token findTokenById(int tokenId) {
        return tokenRepository.findTokenById(tokenId);
    }

    public Token findTokenByValue(String tokenValue) {
        return tokenRepository.findTokenByValue(tokenValue);
    }

    public void delete(Token token) {
        tokenRepository.delete(token);
    }

    public List<Token> findTokensForEntity(Entity entity) {
        return tokenRepository.findTokensForEntity(entity);
    }

    @Transactional
    public void extend(int userId, int tokenId) {
        Token token = findTokenById(tokenId);
        if (!token.getStatus().isValid()) {
            throw new BusinessException("token invalid state for extend action");
        }

        User user = userManager.findById(userId);
        if (!user.hasAccessTo(token)) {
            throw new BusinessException("user has no access to specified token");
        }

        token.extendWithDays(5);
        tokenRepository.save(token);
    }

    @Transactional
    public void create(Entity entity, String mail, String inviter, Url url) {
        Token token = Token.createUserInvitationToken()
                .generateToken()
                .entity(entity)
                .email(mail)
                .build();
        token = tokenRepository.save(token);

        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(inviter)
                .to(mail);
        mailSender.send(userInvitationMail);

        // TODO add updated tms in db
        // TODO add version in db
    }

    public void resend(int tokenId, String inviter, Url url) {
        Token token = findTokenById(tokenId);
        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(inviter)
                .to(token.getEmail());
        mailSender.send(userInvitationMail);
    }
}
