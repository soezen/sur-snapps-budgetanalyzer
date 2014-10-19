package sur.snapps.budgetanalyzer.business.event;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.BaseEntity;
import sur.snapps.budgetanalyzer.domain.event.Event;
import sur.snapps.budgetanalyzer.domain.event.EventWithSubject;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
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

    @Autowired
    private UserManager userManager;

    @Autowired
    private TokenManager tokenManager;

    @Transactional
    public void create(User user, BaseEntity subject, LogEvent logEvent) {
        Event event = new Event();
        event.setTms(DateUtil.now());
        event.setType(logEvent.value());
        event.setUser(user);
        event.setSubjectId(subject == null ? null : subject.getId());
        eventRepository.save(event);
    }

    public List<Event> getEvents(Entity entity) {
        List<Event> events = eventRepository.getEvents(entity);
        return FluentIterable.from(events).transform(new Function<Event, Event>() {
            @Override
            public Event apply(Event originalEvent) {
                switch (originalEvent.getType()) {
                    case ADMIN_TRANSFER:
                        User newAdmin = userManager.findById(originalEvent.getSubjectId());
                        return new EventWithSubject<>(originalEvent, newAdmin);
                    case USER_INVITATION:
                        Token userInvitation = tokenManager.findTokenById(originalEvent.getSubjectId());
                        return new EventWithSubject<>(originalEvent, userInvitation);
                    default:
                        return originalEvent;
                }
            }
        }).toList();
    }
}
