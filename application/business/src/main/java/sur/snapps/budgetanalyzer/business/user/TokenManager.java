package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.business.mail.MailFactory;
import sur.snapps.budgetanalyzer.business.mail.MailSender;
import sur.snapps.budgetanalyzer.domain.event.EventType;
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
    public void restore(User user, int tokenId, Url url) {
        Token token = findTokenById(tokenId);
        if (!user.hasAccessTo(token)) {
            throw new BusinessException("user has no access to specified token");
        }
        if (!token.getStatus().isExpired() && !token.getStatus().isRevoked()) {
            throw new BusinessException("token invalid state for restore action");
        }

        token.restore();
        tokenRepository.save(token);

        // TODO send different or same mail as user invitation?
        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(user.getName())
                .to(token.getEmail());
        mailSender.send(userInvitationMail);
    }

    @Transactional
    public void extend(User user, int tokenId) {
        Token token = findTokenById(tokenId);
        if (!user.hasAccessTo(token)) {
            throw new BusinessException("user has no access to specified token");
        }
        if (!token.getStatus().isValid()) {
            throw new BusinessException("token invalid state for extend action");
        }

        token.extendWithDays(5);
        tokenRepository.save(token);
    }

    @Transactional
    @LogEvent(EventType.USER_INVITATION)
    public void create(User user, String mail, Url url) {
        Token token = Token.createUserInvitationToken()
                .generateToken()
                .entity(user.getEntity())
                .email(mail)
                .build();
        token = tokenRepository.save(token);

        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(user.getName())
                .to(mail);
        mailSender.send(userInvitationMail);

        // TODO add updated tms in db
        // TODO add version in db
    }

    @Transactional
    public void resend(User user, int tokenId, Url url) {
        Token token = findTokenById(tokenId);
        if (!user.hasAccessTo(token)) {
            throw new BusinessException("user has no access to specified token");
        }
        if (!token.getStatus().isValid()) {
            throw new BusinessException("token invalid state for resend action");
        }

        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(user.getName())
                .to(token.getEmail());
        mailSender.send(userInvitationMail);
    }

    @Transactional
    public void revoke(User user, int tokenId) {
        Token token = findTokenById(tokenId);
        if (!user.hasAccessTo(token)) {
            throw new BusinessException("user has no access to specified token");
        }
        if (!token.getStatus().isValid()) {
            throw new BusinessException("token invalid state for revoke action");
        }

        token.revoke();
        tokenRepository.save(token);
    }
}
