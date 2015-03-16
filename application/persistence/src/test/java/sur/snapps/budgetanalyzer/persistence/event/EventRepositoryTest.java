package sur.snapps.budgetanalyzer.persistence.event;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sur.snapps.budgetanalyzer.domain.event.Event;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.RepositoryTest;
import sur.snapps.budgetanalyzer.persistence.user.UserRepository;
import sur.snapps.budgetanalyzer.util.DateUtil;
import sur.snapps.jetta.data.DataObject;
import sur.snapps.jetta.data.DataSet;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataSet(type = User.class)
public class EventRepositoryTest extends RepositoryTest {

    @Autowired
    private EventRepository repository;
    @Autowired
    private UserRepository userRepository;

    @DataObject("FACE_USER_REGISTRATION")
    private Event userRegistrationFace;
    @DataObject("SIMPSONS")
    private Entity theSimpsons;

    @Test
    public void save() throws Exception {
        User homer = userRepository.findById("HOMER_SIMPSON");
        assertNotNull(homer);
        Event newEvent = new Event();
        newEvent.setUser(homer);
        newEvent.setTms(DateUtil.now());
        newEvent.setType(EventType.USER_REGISTRATION);

        repository.save(newEvent);
        repository.flush();

        assertEquals(1, counter.count().from("EVENTS").get());
    }

    @Test
    @DataSet(type = Event.class)
    public void findForEntity() throws Exception {
        List<Event> events = repository.findFor(theSimpsons, 0, 10);
        assertEquals(1, events.size());
        assertEquals("USER_REGISTRATION_HOMER", events.get(0).getId());
    }

    // TODO test pagination of findFor
}