package sur.snapps.budgetanalyzer.business.event;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.annotation.Dummy;
import sur.snapps.budgetanalyzer.domain.event.Event;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.event.EventRepository;

import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.unitils.easymock.EasyMockUnitils.replay;
import static org.junit.Assert.assertNotNull;

/**
 * User: SUR
 * Date: 15/08/14
 * Time: 16:50
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EventManagerTest {

    @TestedObject
    private EventManager manager;

    @Mock
    @InjectIntoByType
    private EventRepository repository;

    @Dummy
    private User user;
    @Dummy
    private Entity entity;
    @Dummy
    private List<Event> events;

    @Mock
    private LogEvent logEvent;

    @Test
    public void testCreateWithoutSubject() {
        Capture<Event> eventCapture = new Capture<>();

        expect(logEvent.value()).andReturn(EventType.USER_INVITATION);
        expect(repository.save(capture(eventCapture))).andReturn(null);
        replay();

        manager.create(user, null, logEvent);

        Event event = eventCapture.getValue();
        assertNotNull(event.getTms());
        assertEquals(EventType.USER_INVITATION, event.getType());
        assertSame(user, event.getUser());
    }

    @Test
    public void testGetEvents() {
        expect(repository.getEvents(entity)).andReturn(events);
        replay();

        List<Event> results = manager.getEvents(entity);

        assertSame(events, results);
    }
}
