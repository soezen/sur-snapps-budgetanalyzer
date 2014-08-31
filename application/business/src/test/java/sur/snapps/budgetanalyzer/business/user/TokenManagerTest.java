package sur.snapps.budgetanalyzer.business.user;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.annotation.Dummy;
import sur.snapps.budgetanalyzer.business.mail.MailFactory;
import sur.snapps.budgetanalyzer.business.mail.SendGridMailSender;
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

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.unitils.easymock.EasyMockUnitils.replay;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 19:12
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TokenManagerTest {

    @TestedObject
    private TokenManager manager;

    @Mock
    @InjectIntoByType
    private TokenRepository repository;

    @Mock
    @InjectIntoByType
    private MailFactory mailFactory;

    @Mock
    @InjectIntoByType
    private SendGridMailSender mailSender;

    @Mock
    private UserInvitationMail userInvitationMail;
    @Mock
    private Token token;
    @Mock
    private User user;
    @Dummy
    private Entity entity;
    @Dummy
    private Email mail;
    @Dummy
    private List<Token> tokens;

    private Url url;

    @Before
    public void init() {
        url = new Url();
        url.setServerName("servername");
        url.setServerPort(2001);
        url.setContextPath("/web");
    }

    @Test
    public void testCreateToken() {
        String inviter = "inviter";
        String tokenValue = "tokenValue";
        Capture<Token> tokenCapture = new Capture<>();

        expect(user.getEntity()).andReturn(entity);
        expect(repository.save(capture(tokenCapture))).andReturn(token);
        expect(mailFactory.createUserInvitationMail()).andReturn(userInvitationMail);
        expect(userInvitationMail.host(url.getServerName())).andReturn(userInvitationMail);
        expect(userInvitationMail.port(url.getServerPort())).andReturn(userInvitationMail);
        expect(userInvitationMail.context(url.getContextPath())).andReturn(userInvitationMail);
        expect(token.value()).andReturn(tokenValue);
        expect(userInvitationMail.token(tokenValue)).andReturn(userInvitationMail);
        expect(user.getName()).andReturn(inviter);
        expect(userInvitationMail.inviter(inviter)).andReturn(userInvitationMail);
        expect(userInvitationMail.to(mail)).andReturn(userInvitationMail);
        mailSender.send(userInvitationMail);
        replay();

        manager.create(user, mail, url);

        Token token = tokenCapture.getValue();
        assertSame(entity, token.entity());
        assertNotNull(token.value());
//        assertTrue(now.plusDays(7).dayOfYear().equals(token.getExpirationDate().dayOfYear()));
    }

    @Test
    public void testFindTokenById() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        replay();

        Token result = manager.findTokenById(id);

        assertSame(token, result);
    }

    @Test
    public void testFindTokenByValue() {
        String value = "value";

        expect(repository.findTokenByValue(value)).andReturn(token);
        replay();

        Token result = manager.findTokenByValue(value);

        assertSame(token, result);
    }

    @Test
    public void testDelete() {
        repository.delete(token);
        replay();

        manager.delete(token);
    }

    @Test
    public void testFindTokensForEntity() {
        expect(repository.findTokensForEntity(entity)).andReturn(tokens);
        replay();

        List<Token> results = manager.findTokensForEntity(entity);

        assertSame(tokens, results);
    }

    @Test
    public void testRestoreSuccess() {
        int id = 1;
        String tokenValue = "value";
        String inviter = "inviter";

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.REVOKED);
        token.restore();
        expect(repository.save(token)).andReturn(token);
        expect(mailFactory.createUserInvitationMail()).andReturn(userInvitationMail);
        expect(userInvitationMail.host(url.getServerName())).andReturn(userInvitationMail);
        expect(userInvitationMail.port(url.getServerPort())).andReturn(userInvitationMail);
        expect(userInvitationMail.context(url.getContextPath())).andReturn(userInvitationMail);
        expect(token.value()).andReturn(tokenValue);
        expect(userInvitationMail.token(tokenValue)).andReturn(userInvitationMail);
        expect(user.getName()).andReturn(inviter);
        expect(userInvitationMail.inviter(inviter)).andReturn(userInvitationMail);
        expect(token.getEmail()).andReturn(mail);
        expect(userInvitationMail.to(mail)).andReturn(userInvitationMail);
        mailSender.send(userInvitationMail);
        replay();

        Token result = manager.restore(user, id, url);

        assertSame(token, result);
    }

    @Test
    public void testRestoreUserHasNoAccess() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(false);
        replay();

        try {
            manager.restore(user, id, url);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_access", error.getErrorCode());
            assertEquals("User has no access to specified token", error.getErrorMessage());
        }
    }

    @Test
    public void testRestoreTokenValid() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.VALID);
        replay();

        try {
            manager.restore(user, id, url);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_state", error.getErrorCode());
            assertEquals("Token invalid state for this action", error.getErrorMessage());
        }
    }

    @Test
    public void testResendSuccess() {
        int id = 1;
        String tokenValue = "value";
        String inviter = "inviter";

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.VALID);

        expect(mailFactory.createUserInvitationMail()).andReturn(userInvitationMail);
        expect(userInvitationMail.host(url.getServerName())).andReturn(userInvitationMail);
        expect(userInvitationMail.port(url.getServerPort())).andReturn(userInvitationMail);
        expect(userInvitationMail.context(url.getContextPath())).andReturn(userInvitationMail);
        expect(token.value()).andReturn(tokenValue);
        expect(userInvitationMail.token(tokenValue)).andReturn(userInvitationMail);
        expect(user.getName()).andReturn(inviter);
        expect(userInvitationMail.inviter(inviter)).andReturn(userInvitationMail);
        expect(token.getEmail()).andReturn(mail);
        expect(userInvitationMail.to(mail)).andReturn(userInvitationMail);
        mailSender.send(userInvitationMail);
        replay();

        Token result = manager.resend(user, id, url);

        assertSame(token, result);
    }

    @Test
    public void testResendUserHasNoAccess() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(false);
        replay();

        try {
            manager.resend(user, id, url);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_access", error.getErrorCode());
            assertEquals("User has no access to specified token", error.getErrorMessage());
        }
    }

    @Test
    public void testResendTokenRevoked() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.REVOKED);
        replay();

        try {
            manager.resend(user, id, url);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_state", error.getErrorCode());
            assertEquals("Token invalid state for this action", error.getErrorMessage());
        }
    }

    @Test
    public void testExtendSuccess() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.VALID);
        token.extendWithDays(5);
        expect(repository.save(token)).andReturn(token);
        replay();

        Token result = manager.extend(user, id);

        assertSame(token, result);
    }

    @Test
    public void testExtendUserHasNoAccess() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(false);
        replay();

        try {
            manager.extend(user, id);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_access", error.getErrorCode());
            assertEquals("User has no access to specified token", error.getErrorMessage());
        }
    }

    @Test
    public void testExtendTokenExpired() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.EXPIRED);
        replay();

        try {
            manager.extend(user, id);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_state", error.getErrorCode());
            assertEquals("Token invalid state for this action", error.getErrorMessage());
        }
    }

    @Test
    public void testRevokeSuccess() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.VALID);
        token.revoke();
        expect(repository.save(token)).andReturn(token);
        replay();

        Token result = manager.revoke(user, id);

        assertSame(token, result);
    }

    @Test
    public void testRevokeUserHasNoAccess() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(false);
        replay();

        try {
            manager.revoke(user, id);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_access", error.getErrorCode());
            assertEquals("User has no access to specified token", error.getErrorMessage());
        }
    }

    @Test
    public void testRevokeTokenExpired() {
        int id = 1;

        expect(repository.findTokenById(id)).andReturn(token);
        expect(user.hasAccessTo(token)).andReturn(true);
        expect(token.getStatus()).andReturn(TokenStatus.EXPIRED);
        replay();

        try {
            manager.revoke(user, id);
            fail();
        } catch (BusinessException error) {
            assertEquals("error.token.invalid_state", error.getErrorCode());
            assertEquals("Token invalid state for this action", error.getErrorMessage());
        }
    }

}
