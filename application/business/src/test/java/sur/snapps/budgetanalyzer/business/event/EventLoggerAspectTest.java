package sur.snapps.budgetanalyzer.business.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.annotation.Dummy;
import sur.snapps.budgetanalyzer.domain.user.User;

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

    @Dummy
    private User user;
    @Dummy
    private LogEvent logEvent;

    @Test
    public void testLogEvent() {
        manager.create(user, logEvent);
        replay();

        logger.logEvent(user, logEvent);
    }
}
