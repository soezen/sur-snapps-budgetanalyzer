package sur.snapps.budgetanalyzer.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.business.mail.MailFactory;
import sur.snapps.budgetanalyzer.business.mail.MailSender;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.mail.Url;
import sur.snapps.budgetanalyzer.domain.mail.UserInvitationMail;
import sur.snapps.budgetanalyzer.domain.user.Email;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.TokenStatus;
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
    public Token restore(User user, int tokenId, Url url) {
        Token token = findTokenById(tokenId);
        checkUserAccess(user, token);
        checkTokenStatus(token, TokenStatus.VALID, false);

        token.restore();
        token = tokenRepository.save(token);

        // TODO-FUNC UC-1 send different or same mail as user invitation?
        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(user.getName())
                .to(token.getEmail());
        mailSender.send(userInvitationMail);
        return token;
    }

    @Transactional
    public Token extend(User user, int tokenId) {
        Token token = findTokenById(tokenId);
        checkUserAccess(user, token);
        checkTokenStatus(token, TokenStatus.VALID, true);

        token.extendWithDays(5);
        return tokenRepository.save(token);
    }

    private void checkTokenStatus(Token token, TokenStatus status, boolean required) {
        if ((required && token.getStatus() != status)
            || (!required && token.getStatus() == status)) {
            throw new BusinessException("error.token.invalid_state", "Token invalid state for this action");
        }
    }

    private void checkUserAccess(User user, Token token) {
        if (!user.hasAccessTo(token)) {
            throw new BusinessException("error.token.invalid_access", "User has no access to specified token");
        }
    }

    @Transactional
    @LogEvent(EventType.USER_INVITATION)
    public void create(User user, Email mail, Url url) {
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

        // TODO-TECH add updated tms in db
        // TODO-TECH add version in db
    }

    @Transactional
    public Token resend(User user, int tokenId, Url url) {
        Token token = findTokenById(tokenId);
        checkUserAccess(user, token);
        checkTokenStatus(token, TokenStatus.VALID, true);

        UserInvitationMail userInvitationMail = mailFactory.createUserInvitationMail()
                .host(url.getServerName())
                .port(url.getServerPort())
                .context(url.getContextPath())
                .token(token.value())
                .inviter(user.getName())
                .to(token.getEmail());
        mailSender.send(userInvitationMail);
        return token;
    }

    @Transactional
    public Token revoke(User user, int tokenId) {
        Token token = findTokenById(tokenId);
        checkUserAccess(user, token);
        checkTokenStatus(token, TokenStatus.VALID, true);

        token.revoke();
        return tokenRepository.save(token);
    }
}
