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
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.user.TokenRepository;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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
        String mail = "mail";
        String inviter = "inviter";
        String tokenValue = "tokenValue";
        Capture<Token> tokenCapture = new Capture<>();

        expect(repository.save(capture(tokenCapture))).andReturn(token);
        expect(mailFactory.createUserInvitationMail()).andReturn(userInvitationMail);
        expect(userInvitationMail.host(url.getServerName())).andReturn(userInvitationMail);
        expect(userInvitationMail.port(url.getServerPort())).andReturn(userInvitationMail);
        expect(userInvitationMail.context(url.getContextPath())).andReturn(userInvitationMail);
        expect(token.value()).andReturn(tokenValue);
        expect(userInvitationMail.token(tokenValue)).andReturn(userInvitationMail);
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
}
