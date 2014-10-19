package sur.snapps.budgetanalyzer.domain.event;

import sur.snapps.budgetanalyzer.domain.BaseEntity;

import javax.persistence.Transient;

/**
 * User: SUR
 * Date: 19/10/14
 * Time: 17:12
 */
public class EventWithSubject<T extends BaseEntity> extends Event {

    @Transient
    private T subject;

    public EventWithSubject(Event event, T subject) {
        this.setId(event.getId());
        this.setSubjectId(event.getSubjectId());
        this.setTms(event.getTms());
        this.setType(event.getType());
        this.setUser(event.getUser());
        this.subject = subject;
    }

    public T getSubject() {
        return subject;
    }

}
