package sur.snapps.budgetanalyzer.business.event;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.domain.BaseEntity;
import sur.snapps.budgetanalyzer.domain.event.Event;
import sur.snapps.budgetanalyzer.domain.event.EventWithSubject;
import sur.snapps.budgetanalyzer.domain.user.Account;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.HistoryRepository;
import sur.snapps.budgetanalyzer.persistence.event.EventRepository;
import sur.snapps.budgetanalyzer.util.DateUtil;

import java.util.List;

import static sur.snapps.budgetanalyzer.util.Comparators.BY_EVENT_TMS;

/**
 * User: SUR
 * Date: 14/06/14
 * Time: 9:13
 */
public class EventManager {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    public void create(User user, BaseEntity subject, LogEvent logEvent) {
        Event event = new Event();
        event.setTms(DateUtil.now());
        event.setType(logEvent.value());
        if (user == null) {
            user = (User) subject;
        }
        event.setUser(user);
        event.setSubjectId(subject == null ? null : subject.getId());
        eventRepository.save(event);
    }

    @Transactional
    public List<Event> findFor(final Entity entity, int pageIndex, int pageSize) {
        List<Event> events = eventRepository.findFor(entity, pageIndex, pageSize);
        return FluentIterable.from(events)
            .transform(ENHANCE_EVENT_WITH_SUBJECT)
            .toSortedList(BY_EVENT_TMS)
            .reverse();
    }

    private final Function<Event, Event> ENHANCE_EVENT_WITH_SUBJECT = new Function<Event, Event>() {
        @Override
        public Event apply(Event originalEvent) {
            switch (originalEvent.getType()) {
                case ADMIN_TRANSFER:
                case USER_REGISTRATION:
                    User newAdmin = historyRepository.find(User.class, originalEvent.getSubjectId(), originalEvent.getTms());
                    return new EventWithSubject<>(originalEvent, newAdmin);
                case USER_INVITATION:
                    Token token = historyRepository.find(Token.class, originalEvent.getSubjectId(), originalEvent.getTms());
                    if (token != null) {
                        return new EventWithSubject<>(originalEvent, token);
                    }
                    return originalEvent;
                case ENTITY_UPDATE:
                    Entity entity = historyRepository.find(Entity.class, originalEvent.getSubjectId(), originalEvent.getTms());
                    return new EventWithSubject<>(originalEvent, entity);
                case ACCOUNT_CREATED:
                    Account account = historyRepository.find(Account.class, originalEvent.getSubjectId(), originalEvent.getTms());
                    return new EventWithSubject<>(originalEvent, account);
                default:
                    return originalEvent;
            }
        }
    };

    // TODO event subject is required and thus subclass can be merged with class

}
