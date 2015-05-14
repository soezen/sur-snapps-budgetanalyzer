package sur.snapps.budgetanalyzer.business.event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.annotation.Dummy;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.User;

import static org.easymock.EasyMock.expect;
import static org.unitils.easymock.EasyMockUnitils.replay;

/**
 * User: SUR
 * Date: 15/08/14
 * Time: 17:01
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EventLoggerAspectTest {

    @TestedObject
    private EventLoggerAspect logger;

    @Mock
    @InjectIntoByType
    private EventManager manager;
    @Mock
    @InjectIntoByType
    private UserManager userManager;

    @Mock
    private SecurityContext context;
    @Mock
    private Authentication authentication;

    @Dummy
    private User user;
    @Dummy
    private LogEvent logEvent;

    @Before
    public void setup() {
        SecurityContextHolder.setContext(context);
    }

    @Test
    public void testLogEventWithoutSubject() {
        expect(context.getAuthentication()).andReturn(authentication);
        expect(authentication.getName()).andReturn("username");
        expect(userManager.findByUsername("username")).andReturn(user);
        manager.create(user, null, logEvent);
        replay();

        logger.logEvent(logEvent, null);
    }
}
