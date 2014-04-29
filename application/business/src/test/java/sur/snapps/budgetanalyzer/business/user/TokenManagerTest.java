package sur.snapps.budgetanalyzer.business.user;

import org.easymock.Capture;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.annotation.Dummy;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.persistence.user.TokenRepository;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

    @Dummy
    private Token token;
    @Dummy
    private Entity entity;

    @Test
    @Ignore
    public void testCreateToken() {
        // TODO fix
        DateTime now = DateTime.now();
        String mail = "mail";
        String inviter = "inviter";
        Capture<Token> tokenCapture = new Capture<>();

        expect(repository.save(capture(tokenCapture))).andReturn(token);
        replay();

        manager.createToken(entity, mail, inviter);

        Token token = tokenCapture.getValue();
        assertSame(entity, token.entity());
        assertNotNull(token.value());
        assertTrue(now.plusDays(7).dayOfYear().equals(token.expirationDate().dayOfYear()));
    }
}
