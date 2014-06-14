package sur.snapps.budgetanalyzer.business.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.domain.event.Event;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.event.EventRepository;
import sur.snapps.budgetanalyzer.util.DateUtil;

import java.util.List;

/**
 * User: SUR
 * Date: 14/06/14
 * Time: 9:13
 */
public class EventManager {

    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public void create(User user, LogEvent logEvent) {
        Event event = new Event();
        event.setTms(DateUtil.now());
        event.setType(logEvent.value());
        event.setUser(user);
        eventRepository.save(event);
    }

    public List<Event> getEvents(Entity entity) {
        return eventRepository.getEvents(entity);
    }
}
